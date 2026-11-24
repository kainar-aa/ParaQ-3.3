package kz.paraq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class QrScannerTest {
  @Test void readTheSameCodeThatTheReaderReceives() throws Exception {
    String code="PQ-A1B2C3D4E5F6";
    var image=MatrixToImageWriter.toBufferedImage(new MultiFormatWriter().encode(code,BarcodeFormat.QR_CODE,300,300));
    var out=new ByteArrayOutputStream();ImageIO.write(image,"PNG",out);
    assertThat(QrScanner.decode(out.toByteArray())).isEqualTo(code);
  }
  @Test void rejectRandomImageWithoutQr() throws Exception {
    var image=new java.awt.image.BufferedImage(300,300,java.awt.image.BufferedImage.TYPE_INT_RGB);
    var out=new ByteArrayOutputStream();ImageIO.write(image,"PNG",out);
    assertThatThrownBy(()->QrScanner.decode(out.toByteArray())).isInstanceOf(ResponseStatusException.class);
  }
}
