package kz.paraq;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Decode the actual QR pixels with ZXing; the result must match our reservation code. */
final class QrScanner {
  private QrScanner(){}
  static String decode(byte[] data) throws IOException{
    if(data==null || data.length==0 || data.length>2_000_000)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image too large or empty");
    java.awt.image.BufferedImage image;
    // Check image dimensions BEFORE full decoding to avoid decompression bombs.
    try(ImageInputStream stream=ImageIO.createImageInputStream(new ByteArrayInputStream(data))){
      if(stream==null)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported QR image");
      var readers=ImageIO.getImageReaders(stream);
      if(!readers.hasNext())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unsupported image format; use JPEG or PNG");
      var reader=readers.next();
      try{
        reader.setInput(stream,true,true);
        if((long)reader.getWidth(0)*reader.getHeight(0)>16_000_000L)
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image dimensions too large");
        image=reader.read(0);
      }finally{reader.dispose();}
    }
    BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
    try{
      var qr=new MultiFormatReader().decode(bitmap,Map.of(
        DecodeHintType.POSSIBLE_FORMATS,List.of(BarcodeFormat.QR_CODE),
        DecodeHintType.TRY_HARDER,Boolean.TRUE));
      String code=qr.getText().trim().toUpperCase(Locale.ROOT);
      if(!code.matches("PQ-[0-9A-F]{12}"))
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not a ParaQ reservation QR code");
      return code;
    }catch(NotFoundException ex){
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"QR code not found in the image");
    }
  }
}
