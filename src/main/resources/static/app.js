'use strict';
const translations = {
  kk:{qrScannerTitle:'QR-код сканері',qrScannerHint:'Оқырманның бронь QR-кодын телефон камерасымен немесе сурет арқылы оқыңыз. Кітап беру бөлек расталады.',qrCameraStartHint:'Камераны қосыңыз немесе QR суретін таңдаңыз',qrStartCamera:'Камераны ашу',qrStopCamera:'Камераны тоқтату',qrUpload:'QR суретін таңдау',qrManual:'Немесе QR-код нөмірін енгізіңіз',qrVerify:'QR тексеру',qrScanHelp:'QR тексеру кітапты автоматты түрде бермейді.',qrReady:'QR расталды',qrBook:'Кітап',qrReader:'Оқырман',qrCameraError:'Камера ашылмады. Рұқсатты тексеріңіз немесе QR суретін таңдаңыз.',qrNoImage:'QR суретін таңдаңыз',qrInvalid:'Дұрыс ParaQ QR-кодын енгізіңіз',qrNotFound:'Суреттен QR табылмады. Жарықты және фокусты тексеріңіз.',qrUnrecognized:'Бұл бронь табылмады',qrIssueHint:'Кітап беру үшін жоғарғы Кітап беру батырмасын басыңыз.',qrReturnHint:'Кітапты қабылдау үшін жоғарғы Қайтару батырмасын басыңыз.',qrOtherStatus:'Бұл бронь бойынша кітап беру не қайтару мүмкін емес.',themeSoft:'Жұмсақ бейтарап режим',themeLightShort:'Күн',themeDarkShort:'Түн',themeSoftShort:'Жұмсақ',readerSepia:'Қағаз',readerLight:'Жарық',readerDark:'Түнгі',hybrid:'Қағаз + электронды',brandSub:'электронды кітапхана',home:'Басты бет',catalog:'Каталог',online:'Онлайн оқу',favorites:'Таңдаулылар',mybooks:'Менің кітаптарым',account:'Жеке кабинет',login:'Кіру',logout:'Шығу',register:'Тіркелу',heroKicker:'КІТАП ОҚУҒА ЖАҢА КӨЗҚАРАС',heroTitle:'Әр кітап — жаңа әлемге ашылған парақ',heroDesc:'Кітапты табыңыз, онлайн оқыңыз немесе кітапханадан алып кетуге броньдаңыз.',viewBooks:'Кітаптарды қарау',startReading:'Онлайн оқу',featureFind:'Қажетті кітапты табыңыз',featureReserve:'Күн таңдап броньдаңыз',featureRead:'Өзіңізге ыңғайлы оқыңыз',collection:'БІЗДІҢ ҚОР',popular:'Кітаптар топтамасы',allBooks:'Барлық кітаптар →',catalogHint:'Физикалық және электронды кітаптарды бір жерден іздеңіз.',filters:'Сүзгілер',search:'Іздеу',searchPlace:'Атауы, авторы, жанры...',kind:'Кітап түрі',all:'Барлығы',physical:'Қағаз кітаптар',electronic:'Электронды кітаптар',genre:'Жанр',bookLang:'Кітап тілі',libraryReader:'PARAQ READER',onlineHint:'Электронды файлдары бар кітаптар. Мәтін мен қаріп өзіңізге ыңғайланады.',favoritesHint:'Кейін оқитын кітаптарыңыз осы жерде сақталады.',mybooksHint:'Броньдар, кезек және оқу прогресі',accountHint:'Аккаунт және кітап алуға қажетті байланыс ақпараты.',profile:'Профиль',fullName:'Аты-жөні',email:'Электронды пошта',phone:'Телефон',address:'Мекенжай (міндетті емес)',profileNotice:'Қағаз кітапты броньдау үшін телефон нөмірі қажет. ЖСН сұралмайды.',save:'Сақтау',security:'Қауіпсіздік',oldPassword:'Қазіргі пароль',newPassword:'Жаңа пароль (6+)',changePassword:'Пароль өзгерту',readingStats:'Оқу статистикасы',staffPanel:'Кітапханашы панелі',staffHint:'Броньдар, кітап беру, QR тексеру және қайтару',issue:'Кітап беру',returnBook:'Кітапты қайтару',qrCode:'QR-код нөмірі',condition:'Кітап жағдайы',good:'Жақсы',damaged:'Бүлінген',lost:'Жоғалған',reservations:'Броньдар',adminPanel:'Басқару панелі',adminHint:'Кітаптар, пайдаланушылар, қызметкерлер және есептер',dashboard:'Шолу',manageBooks:'Кітаптарды басқару',manageUsers:'Пайдаланушылар',manageStaff:'Қызметкерлер',reports:'Есептер',logs:'Әрекеттер журналы',footer:'Кітапхана және онлайн оқу жүйесі',notifications:'Хабарламалар',markRead:'Оқылды',username:'Логин',password:'Пароль',reserve:'Броньдау',pickupDay:'Алып кету күні',reserveHint:'Бүгін, ертең немесе бүрсігүні таңдаңыз. Кітап таңдалған күні жұмыс уақыты ішінде беріледі.',confirmReserve:'Броньды растау',onlineShort:'Оқу',favoritesShort:'Сақталған',available:'Бос дана',copies:'Дана',author:'Автор',read:'Оқу',more:'Толығырақ',reserveNow:'Броньдау',waitlist:'Кезекке тұру',waitlistTitle:'Күту тізімі',reviews:'Пікірлер',rate:'Бағалау',noBooks:'Кітаптар табылмады',emptyFavorites:'Сақталған кітаптар жоқ',emptyReservations:'Әзірге бронь жоқ',emptyReading:'Сіз әлі электронды кітап оқымағансыз',emptyNotifications:'Жаңа хабарлама жоқ',emptyQueue:'Күту тізімі бос',loginNeeded:'Алдымен аккаунтқа кіріңіз',saved:'Сақталды',done:'Орындалды',failed:'Қате шықты',loading:'Жүктелуде...',physicalTab:'Физикалық кітаптар',electronicTab:'Электронды кітаптар',date:'Күні',status:'Мәртебе',cancel:'Бас тарту',renew:'Мерзімін ұзарту',qr:'QR көрсету',queue:'Кезек',back:'Артқа',readPercent:'Оқылды',noAccess:'Бұл бөлімге рұқсат жоқ',accountCreated:'Аккаунт жасалды. Енді кіріңіз.',passwordChanged:'Пароль өзгерді. Қайта кіріңіз.',contactRequired:'Алдымен профильге телефон нөмірін қосыңыз',fileMissing:'Электронды файл жоқ',progressSaved:'Оқу барысы сақталды',addBook:'Кітап қосу',edit:'Өзгерту',upload:'Файл жүктеу',archive:'Архивке жіберу',public:'Көрінетін',name:'Атауы',langTitle:'Атау аудармалары',titleKk:'Қазақша атауы',titleRu:'Орысша атауы',titleEn:'Ағылшынша атауы',description:'Сипаттама',coverUrl:'Мұқаба суретінің HTTPS сілтемесі',bookCount:'Кітаптар',users:'Пайдаланушылар',activeLoans:'Белсенді броньдар',staff:'Қызметкерлер',completedLoans:'Қайтарылған',addStaff:'Қызметкер қосу',ban:'Бұғаттау',unban:'Ашу',deleteAccount:'Аккаунтты жою',days:'Күн саны',banTemporary:'Уақытша блок',banForever:'Тұрақты блок',confirm:'Осы әрекетті растайсыз ба?',details:'Мәлімет',created:'Тіркелген',active:'Белсенді',blocked:'Бұғатталған',waiting:'Күтіп тұр',filename:'TXT / PDF / EPUB',progress:'Прогресс',view:'Көру',apply:'Қолдану',rating:'Рейтинг',reviewPlace:'Кітап туралы пікір жазыңыз...',rateSave:'Пікірді сақтау',endReader:'Оқуды аяқтау',passwordHint:'Кемінде 6 таңба',staffNav:'Қызметкер панелі',adminNav:'Басқару',totalFound:'Кітап саны',accountRequired:'Тіркелу қажет',emptyTable:'Әзірше жазба жоқ',kazakh:'Қазақша',russian:'Орысша',english:'Ағылшынша',themeSystem:'Жүйелік режим: құрылғы баптауларына сәйкес',themeLight:'Күндізгі режим',themeDark:'Түнгі режим',themeSepia:'Қағаз түсті режим',invalidCredentials:'Логин немесе пароль қате'},
  ru:{qrScannerTitle:'Сканер QR-кода',qrScannerHint:'Сканируйте QR бронирования камерой телефона или загрузите изображение. Выдача книги требует подтверждения.',qrCameraStartHint:'Включите камеру или выберите изображение QR',qrStartCamera:'Включить камеру',qrStopCamera:'Остановить камеру',qrUpload:'Выбрать фото QR',qrManual:'Или введите код вручную',qrVerify:'Проверить QR',qrScanHelp:'Проверка QR не выдаёт книгу автоматически.',qrReady:'QR проверен',qrBook:'Книга',qrReader:'Читатель',qrCameraError:'Не удалось включить камеру. Разрешите доступ или выберите фото QR.',qrNoImage:'Выберите фото QR',qrInvalid:'Введите корректный QR-код ParaQ',qrNotFound:'На изображении нет QR. Проверьте освещение и фокус.',qrUnrecognized:'Бронь не найдена',qrIssueHint:'Для выдачи нажмите «Выдать книгу» в форме выше.',qrReturnHint:'Для возврата нажмите «Вернуть книгу» в форме выше.',qrOtherStatus:'Эту бронь нельзя выдать или вернуть.',themeSoft:'Мягкая нейтральная тема',themeLightShort:'День',themeDarkShort:'Ночь',themeSoftShort:'Мягкая',readerSepia:'Бумага',readerLight:'Светлая',readerDark:'Тёмная',hybrid:'Бумага + электронная',brandSub:'электронная библиотека',home:'Главная',catalog:'Каталог',online:'Читать онлайн',favorites:'Избранное',mybooks:'Мои книги',account:'Личный кабинет',login:'Войти',logout:'Выйти',register:'Регистрация',heroKicker:'НОВЫЙ ВЗГЛЯД НА ЧТЕНИЕ',heroTitle:'Каждая книга — дверь в новый мир',heroDesc:'Находите книги, читайте онлайн или бронируйте для получения в библиотеке.',viewBooks:'Смотреть книги',startReading:'Читать онлайн',featureFind:'Найдите нужную книгу',featureReserve:'Забронируйте на дату',featureRead:'Читайте с комфортом',collection:'НАША КОЛЛЕКЦИЯ',popular:'Подборка книг',allBooks:'Все книги →',catalogHint:'Бумажные и электронные книги в одном каталоге.',filters:'Фильтры',search:'Поиск',searchPlace:'Название, автор, жанр...',kind:'Тип книги',all:'Все',physical:'Бумажные книги',electronic:'Электронные книги',genre:'Жанр',bookLang:'Язык книги',libraryReader:'PARAQ READER',onlineHint:'Книги с доступными файлами. Удобный размер шрифта и фон.',favoritesHint:'Сохранённые книги для чтения в будущем.',mybooksHint:'Бронирования, очередь и прогресс чтения',accountHint:'Аккаунт и контактные данные для получения книг.',profile:'Профиль',fullName:'ФИО',email:'Электронная почта',phone:'Телефон',address:'Адрес (необязательно)',profileNotice:'Для бронирования бумажной книги нужен номер телефона. ИИН не требуется.',save:'Сохранить',security:'Безопасность',oldPassword:'Текущий пароль',newPassword:'Новый пароль (6+)',changePassword:'Сменить пароль',readingStats:'Статистика чтения',staffPanel:'Панель сотрудника',staffHint:'Брони, выдача, QR-проверка и возврат',issue:'Выдать книгу',returnBook:'Принять возврат',qrCode:'Номер QR-кода',condition:'Состояние книги',good:'Хорошее',damaged:'Повреждена',lost:'Утеряна',reservations:'Брони',adminPanel:'Панель управления',adminHint:'Книги, пользователи, сотрудники и отчёты',dashboard:'Обзор',manageBooks:'Управление книгами',manageUsers:'Пользователи',manageStaff:'Сотрудники',reports:'Отчёты',logs:'Журнал действий',footer:'Библиотека и чтение онлайн',notifications:'Уведомления',markRead:'Прочитано',username:'Логин',password:'Пароль',reserve:'Бронирование',pickupDay:'Дата получения',reserveHint:'Выберите сегодня, завтра или послезавтра. Книгу нужно получить в рабочие часы.',confirmReserve:'Подтвердить бронь',onlineShort:'Читать',favoritesShort:'Сохранено',available:'Доступно',copies:'Экземпляры',author:'Автор',read:'Читать',more:'Подробнее',reserveNow:'Забронировать',waitlist:'Встать в очередь',waitlistTitle:'Очередь',reviews:'Отзывы',rate:'Оценить',noBooks:'Книги не найдены',emptyFavorites:'Нет избранных книг',emptyReservations:'Пока нет броней',emptyReading:'Вы пока не читали электронные книги',emptyNotifications:'Нет новых уведомлений',emptyQueue:'Очередь пуста',loginNeeded:'Сначала войдите в аккаунт',saved:'Сохранено',done:'Выполнено',failed:'Произошла ошибка',loading:'Загрузка...',physicalTab:'Бумажные книги',electronicTab:'Электронные книги',date:'Дата',status:'Статус',cancel:'Отменить',renew:'Продлить',qr:'Показать QR',queue:'Очередь',back:'Назад',readPercent:'Прочитано',noAccess:'Нет доступа к этому разделу',accountCreated:'Аккаунт создан. Теперь войдите.',passwordChanged:'Пароль изменён. Войдите снова.',contactRequired:'Сначала добавьте телефон в профиль',fileMissing:'Файл электронной книги отсутствует',progressSaved:'Прогресс сохранён',addBook:'Добавить книгу',edit:'Изменить',upload:'Загрузить файл',archive:'В архив',public:'Опубликована',name:'Название',langTitle:'Переводы названия',titleKk:'Название на казахском',titleRu:'Название на русском',titleEn:'Название на английском',description:'Описание',coverUrl:'HTTPS-ссылка на обложку',bookCount:'Книг',users:'Пользователи',activeLoans:'Активные брони',staff:'Сотрудники',completedLoans:'Возвращено',addStaff:'Добавить сотрудника',ban:'Заблокировать',unban:'Разблокировать',deleteAccount:'Удалить аккаунт',days:'Количество дней',banTemporary:'Временная блокировка',banForever:'Постоянная блокировка',confirm:'Подтвердите действие?',details:'Детали',created:'Дата регистрации',active:'Активный',blocked:'Заблокирован',waiting:'Ожидает',filename:'TXT / PDF / EPUB',progress:'Прогресс',view:'Просмотр',apply:'Применить',rating:'Рейтинг',reviewPlace:'Напишите отзыв о книге...',rateSave:'Сохранить отзыв',endReader:'Завершить чтение',passwordHint:'Минимум 6 символов',staffNav:'Панель сотрудника',adminNav:'Управление',totalFound:'Книг найдено',accountRequired:'Нужна регистрация',emptyTable:'Нет записей',kazakh:'Казахский',russian:'Русский',english:'Английский',themeSystem:'Системная тема: по настройкам устройства',themeLight:'Дневной режим',themeDark:'Ночной режим',themeSepia:'Книжный режим',invalidCredentials:'Неверный логин или пароль'},
  en:{qrScannerTitle:'QR code scanner',qrScannerHint:'Scan a reservation QR code with a camera or upload an image. Issuing a book requires a separate confirmation.',qrCameraStartHint:'Start camera or select a QR image',qrStartCamera:'Start camera',qrStopCamera:'Stop camera',qrUpload:'Choose QR image',qrManual:'Or enter QR code manually',qrVerify:'Verify QR',qrScanHelp:'Scanning does not automatically issue a book.',qrReady:'QR verified',qrBook:'Book',qrReader:'Reader',qrCameraError:'Could not open the camera. Allow camera access or choose an image.',qrNoImage:'Choose a QR image',qrInvalid:'Enter a valid ParaQ QR code',qrNotFound:'No QR found in image. Check lighting and focus.',qrUnrecognized:'Reservation not found',qrIssueHint:'Press Issue book in the form above to confirm.',qrReturnHint:'Press Return book in the form above to confirm.',qrOtherStatus:'This reservation cannot be issued or returned.',themeSoft:'Soft neutral theme',themeLightShort:'Day',themeDarkShort:'Night',themeSoftShort:'Soft',readerSepia:'Paper',readerLight:'Light',readerDark:'Dark',hybrid:'Print + eBook',brandSub:'digital library',home:'Home',catalog:'Catalog',online:'Read online',favorites:'Favorites',mybooks:'My books',account:'My account',login:'Log in',logout:'Log out',register:'Register',heroKicker:'A BETTER WAY TO READ',heroTitle:'Every book opens a new world',heroDesc:'Discover books, read online, or reserve a copy to collect at the library.',viewBooks:'Explore books',startReading:'Read online',featureFind:'Find your next book',featureReserve:'Pick a collection day',featureRead:'Read in comfort',collection:'OUR COLLECTION',popular:'Featured books',allBooks:'All books →',catalogHint:'Physical and electronic books in one catalog.',filters:'Filters',search:'Search',searchPlace:'Title, author, genre...',kind:'Book type',all:'All',physical:'Physical books',electronic:'E-books',genre:'Genre',bookLang:'Book language',libraryReader:'PARAQ READER',onlineHint:'Books with available files. Adjustable font and background.',favoritesHint:'Books you saved for later.',mybooksHint:'Reservations, waitlist, and reading progress',accountHint:'Your account and contact details for collecting books.',profile:'Profile',fullName:'Full name',email:'Email',phone:'Phone',address:'Address (optional)',profileNotice:'A phone number is needed to reserve physical books. National ID is not required.',save:'Save',security:'Security',oldPassword:'Current password',newPassword:'New password (6+)',changePassword:'Change password',readingStats:'Reading stats',staffPanel:'Staff panel',staffHint:'Reservations, issuing, QR checks and returns',issue:'Issue book',returnBook:'Return book',qrCode:'QR code',condition:'Book condition',good:'Good',damaged:'Damaged',lost:'Lost',reservations:'Reservations',adminPanel:'Admin panel',adminHint:'Books, readers, staff and reports',dashboard:'Overview',manageBooks:'Manage books',manageUsers:'Readers',manageStaff:'Staff',reports:'Reports',logs:'Activity log',footer:'Library and online reading',notifications:'Notifications',markRead:'Mark read',username:'Username',password:'Password',reserve:'Reserve',pickupDay:'Pickup date',reserveHint:'Choose today, tomorrow or the day after. Collect during library hours.',confirmReserve:'Confirm reservation',onlineShort:'Read',favoritesShort:'Saved',available:'Available',copies:'Copies',author:'Author',read:'Read',more:'Details',reserveNow:'Reserve',waitlist:'Join waitlist',waitlistTitle:'Waitlist',reviews:'Reviews',rate:'Rate',noBooks:'No books found',emptyFavorites:'No saved books yet',emptyReservations:'No reservations yet',emptyReading:'You have not started reading',emptyNotifications:'No new notifications',emptyQueue:'No books on the waitlist',loginNeeded:'Please log in first',saved:'Saved',done:'Done',failed:'Something went wrong',loading:'Loading...',physicalTab:'Physical books',electronicTab:'E-books',date:'Date',status:'Status',cancel:'Cancel',renew:'Renew',qr:'Show QR',queue:'Waitlist',back:'Back',readPercent:'Read',noAccess:'You do not have access',accountCreated:'Account created. Log in now.',passwordChanged:'Password changed. Log in again.',contactRequired:'Add a phone number to your profile first',fileMissing:'Electronic book file missing',progressSaved:'Reading progress saved',addBook:'Add book',edit:'Edit',upload:'Upload file',archive:'Archive',public:'Published',name:'Title',langTitle:'Title translations',titleKk:'Kazakh title',titleRu:'Russian title',titleEn:'English title',description:'Description',coverUrl:'HTTPS cover URL',bookCount:'Books',users:'Readers',activeLoans:'Active reservations',staff:'Staff',completedLoans:'Returned',addStaff:'Add staff',ban:'Ban',unban:'Unban',deleteAccount:'Delete account',days:'Number of days',banTemporary:'Temporary ban',banForever:'Permanent ban',confirm:'Confirm this action?',details:'Details',created:'Joined',active:'Active',blocked:'Blocked',waiting:'Waiting',filename:'TXT / PDF / EPUB',progress:'Progress',view:'View',apply:'Apply',rating:'Rating',reviewPlace:'Write a review...',rateSave:'Save review',endReader:'Finish reading',passwordHint:'At least 6 characters',staffNav:'Staff panel',adminNav:'Admin',totalFound:'Books found',accountRequired:'Registration required',emptyTable:'No records',kazakh:'Kazakh',russian:'Russian',english:'English',themeSystem:'System theme: follows device settings',themeLight:'Day mode',themeDark:'Night mode',themeSepia:'Paper mode',invalidCredentials:'Incorrect username or password'}
};
const state={lang:localStorage.getItem('paraq-lang')||'kk',theme:({sepia:'soft',system:'soft'}[localStorage.getItem('paraq-theme')]||localStorage.getItem('paraq-theme')||'light'),user:null,books:[],favorites:[],reservations:[],waitlist:[],progress:[],notices:[],page:'home',tab:'physical',admin:'dashboard',csrf:'',csrfHeader:'X-XSRF-TOKEN',chosenBook:0,readerBook:0,readerPercent:0,readerSize:Number(localStorage.getItem('paraq-reader-size')||20),readerTheme:localStorage.getItem('paraq-reader-theme')||'sepia',authTab:'login',adminBooks:[]};
const $=id=>document.getElementById(id);
const t=k=>translations[state.lang]?.[k]||translations.kk[k]||k;
const esc=v=>String(v==null?'':v).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const localDate=d=>{const x=new Date(d+'T12:00:00');return new Intl.DateTimeFormat(state.lang==='kk'?'kk-KZ':state.lang==='ru'?'ru-RU':'en-US',{day:'numeric',month:'short',year:'numeric'}).format(x)};
const dateOffset=n=>{const d=new Date();d.setDate(d.getDate()+n);return [d.getFullYear(),String(d.getMonth()+1).padStart(2,'0'),String(d.getDate()).padStart(2,'0')].join('-')};
const title=b=>b['title_'+state.lang]||b.title;
// Genre codes remain stable in the database; only visible labels are localized.
const genreNames={
  Classic:{kk:'Классика',ru:'Классика',en:'Classics'},
  Detective:{kk:'Детектив',ru:'Детектив',en:'Detective'},
  Education:{kk:'Білім беру',ru:'Образование',en:'Education'},
  ShortStory:{kk:'Қысқа әңгіме',ru:'Короткий рассказ',en:'Short story'},
  Novel:{kk:'Роман',ru:'Роман',en:'Novel'},
  Fiction:{kk:'Көркем әдебиет',ru:'Художественная литература',en:'Fiction'},
  Fantasy:{kk:'Фэнтези',ru:'Фэнтези',en:'Fantasy'},
  History:{kk:'Тарих',ru:'История',en:'History'},
  Poetry:{kk:'Поэзия',ru:'Поэзия',en:'Poetry'},
  Science:{kk:'Ғылым',ru:'Наука',en:'Science'},
  Other:{kk:'Басқа',ru:'Другое',en:'Other'}
};
const genreLabel=g=>(genreNames[String(g||'')]||{})[state.lang]||g||'';

const statusLabel=s=>({kk:{RESERVED:'Броньдалған',READY:'Дайын',ISSUED:'Берілген',RETURNED:'Қайтарылған',CANCELLED:'Болдырылмаған',EXPIRED:'Мерзімі өткен',WAITING:'Кезекте',OFFERED:'Кезек берілді'},ru:{RESERVED:'Забронировано',READY:'Готово',ISSUED:'Выдано',RETURNED:'Возвращено',CANCELLED:'Отменено',EXPIRED:'Истекло',WAITING:'В очереди',OFFERED:'Предложено'},en:{RESERVED:'Reserved',READY:'Ready',ISSUED:'Issued',RETURNED:'Returned',CANCELLED:'Cancelled',EXPIRED:'Expired',WAITING:'Waiting',OFFERED:'Offered'}}[state.lang]||{})[s]||s;
const isLogged=()=>state.user&&state.user.role!=='GUEST';
const isStaff=()=>isLogged()&&['ADMIN','LIBRARIAN'].includes(state.user.role);
const isAdmin=()=>isLogged()&&state.user.role==='ADMIN';
const toast=message=>{const x=$('toast');x.textContent=message;x.classList.add('show');clearTimeout(x.timer);x.timer=setTimeout(()=>x.classList.remove('show'),3200)};
const modal=id=>$(id).showModal();
const dialogClose=id=>{if($(id)?.open)$(id).close()};
const empty=k=>`<div class="empty-state"><b>◯</b>${esc(t(k))}</div>`;
async function csrf(){try{const r=await fetch('/api/auth/csrf',{credentials:'same-origin'});if(r.ok){let x=await r.json();state.csrf=x.token;state.csrfHeader=x.headerName}}catch(e){console.warn('CSRF unavailable',e)}}
async function api(url,opts={}){
  const o={credentials:'same-origin',...opts};o.headers={...opts.headers};
  if(o.body&&!(o.body instanceof FormData)&&typeof o.body!=='string'){o.headers['Content-Type']='application/json';o.body=JSON.stringify(o.body)}
  if(o.method&&!['GET','HEAD'].includes(o.method.toUpperCase())){
    if(!state.csrf)await csrf();o.headers[state.csrfHeader]=state.csrf;
  }
  const res=await fetch(url,o);const ct=res.headers.get('content-type')||'';
  const data=ct.includes('json')?await res.json().catch(()=>({})):ct.includes('text')?await res.text():res;
  if(!res.ok){if(res.status===403||res.status===401){if(!isLogged())toast(t('loginNeeded'))}throw new Error((typeof data==='object'&&data.error)||`${res.status} ${res.statusText}`)}
  return data;
}
async function loadUser(){try{state.user=await api('/api/auth/me')}catch(e){state.user={role:'GUEST'}}}
async function refreshAll(){
  try{state.books=await api('/api/books');if(isLogged()){let res=await Promise.allSettled([api('/api/favorites'),api('/api/reservations'),api('/api/waitlist'),api('/api/progress'),api('/api/notifications')]);
    [state.favorites,state.reservations,state.waitlist,state.progress,state.notices]=res.map(x=>x.status==='fulfilled'?x.value:[])
  }else{state.favorites=[];state.reservations=[];state.waitlist=[];state.progress=[];state.notices=[]}
  }catch(e){toast(e.message)}
  updateHeader();renderCurrent();
}
function initTheme(){
  const desired=['light','dark','soft'].includes(state.theme)?state.theme:'light';
  document.documentElement.dataset.theme=desired;
  $('theme').value=desired;
  const names={light:'themeLight',dark:'themeDark',soft:'themeSoft'};
  $('theme').title=t(names[desired]);
  $('theme').setAttribute('aria-label',t(names[desired]));
  const color={light:'#eee9e0',dark:'#1b1a25',soft:'#dcd5c9'}[desired];
  const meta=document.querySelector('meta[name="theme-color"]');if(meta)meta.content=color;
}
function setTheme(value){
  if(!['light','dark','soft'].includes(value))return;
  state.theme=value;localStorage.setItem('paraq-theme',value);
  initTheme();toast(t(({light:'themeLight',dark:'themeDark',soft:'themeSoft'})[value]));
}
function setLanguage(value){state.lang=value;localStorage.setItem('paraq-lang',value);document.documentElement.lang=value;$('lang').value=value;applyTranslations();renderCurrent();updateHeader();initTheme()}
function applyTranslations(){document.querySelectorAll('[data-i18n]').forEach(e=>e.textContent=t(e.dataset.i18n));document.querySelectorAll('[data-placeholder]').forEach(e=>e.placeholder=t(e.dataset.placeholder));document.title='ParaQ — '+t('catalog');$('bell').title=t('notifications');$('bell').setAttribute('aria-label',t('notifications'))}
function updateHeader(){
  $('profile-short').hidden=!isLogged();$('login-short').hidden=!!isLogged();$('unread').hidden=!state.notices.some(n=>!n.seen);
  document.querySelectorAll('.desktop-nav button,.mobile-nav button').forEach(b=>b.classList.toggle('active',b.dataset.nav===state.page));
  $('bell').hidden=!isLogged();
  // Admin and librarian navigation are added as separate destinations, not mixed with reader tabs.
  for(const id of ['staff-link','admin-link'])$(id)?.remove();
  if(isStaff()){const b=document.createElement('button');b.id='staff-link';b.dataset.nav='staff';b.textContent=t('staffNav');document.querySelector('.desktop-nav').append(b)}
  if(isAdmin()){const b=document.createElement('button');b.id='admin-link';b.dataset.nav='admin';b.textContent=t('adminNav');document.querySelector('.desktop-nav').append(b)}
  if(isStaff()&&window.matchMedia('(max-width:920px)').matches){
    // Staff/admin tabs are also available inside the profile page on phones.
  }
}
function go(page){
  if(page!=='staff')stopStaffQrScan();
  if(['favorites','mybooks','account','staff','admin'].includes(page)&&!isLogged()){openAuth();return}
  if(page==='admin'&&!isAdmin()){toast(t('noAccess'));return}
  if(page==='staff'&&!isStaff()){toast(t('noAccess'));return}
  state.page=page;document.querySelectorAll('.page').forEach(x=>x.classList.toggle('is-active',x.id==='page-'+page));
  document.querySelectorAll('.desktop-nav button,.mobile-nav button').forEach(b=>b.classList.toggle('active',b.dataset.nav===page));
  renderCurrent();window.scrollTo({top:0,behavior:'instant'});location.hash=page;
}
function coverColor(b){const palette=['#7d8d78','#a98876','#718c91','#8d806c','#738267','#a58176','#8b8983'];return palette[Number(b.id)%palette.length]}
function cover(b){const txt=esc(title(b)),auth=esc(b.author);const url=String(b.cover_url||'');
  return `<div class="book-cover" style="--cover:${coverColor(b)}"><small>PARAQ • LIBRARY</small><b>${txt}</b><small>${auth}</small>${url.startsWith('https://')?`<img loading="lazy" alt="" src="${esc(url)}" onerror="this.remove()">`:''}</div>`;
}
function card(b){const fav=state.favorites.some(f=>Number(f.id)===Number(b.id));return `<article class="book-card">
  <button type="button" data-action="favorite" data-id="${b.id}" class="heart ${fav?'active':''}" title="${esc(t('favorites'))}" aria-label="${esc(t('favorites'))}">${fav?'♥':'♡'}</button>
  <button type="button" class="cover-open" data-action="detail" data-id="${b.id}">${cover(b)}</button>
  <h3 title="${esc(title(b))}">${esc(title(b))}</h3><p class="muted">${esc(b.author)}</p>
  <div class="card-bottom"><span class="book-type">${b.online&&Number(b.copies)>0?esc(t('hybrid')):b.online?esc(t('electronic')):esc(t('physical'))}</span><button class="link-btn" data-action="detail" data-id="${b.id}">${esc(t('more'))} →</button></div>
  </article>`}
function renderBooks(list,target,emptyKey='noBooks'){$(target).innerHTML=list.length?list.map(card).join(''):empty(emptyKey)}
function filteredBooks(){const q=$('search').value.trim().toLowerCase(),format=$('filter-kind').value,genre=$('filter-genre').value,language=$('filter-language').value;
 return state.books.filter(b=>(!q||[b.title,b.title_kk,b.title_ru,b.title_en,b.author,b.genre,genreLabel(b.genre)].some(s=>String(s||'').toLowerCase().includes(q)))&&
 (format==='all'||(format==='online'?b.online:Number(b.copies)>0))&&(genre==='all'||b.genre===genre)&&(language==='all'||b.language===language));}
function renderCatalog(){const genres=[...new Set(state.books.map(b=>b.genre).filter(Boolean))].sort((a,b)=>genreLabel(a).localeCompare(genreLabel(b),state.lang));const select=$('filter-genre'),before=select.value;
 select.innerHTML=`<option value="all">${esc(t('all'))}</option>`+genres.map(g=>`<option value="${esc(g)}">${esc(genreLabel(g))}</option>`).join('');select.value=genres.includes(before)?before:'all';
 let list=filteredBooks();$('catalog-count').textContent=`${list.length} · ${t('totalFound')}`;renderBooks(list,'catalog-books');}
function renderCurrent(){switch(state.page){case 'home':renderBooks(state.books.slice(0,4),'home-books');break;case 'catalog':renderCatalog();break;case 'online':renderBooks(state.books.filter(b=>b.online),'online-books');break;case 'favorites':renderBooks(state.books.filter(b=>state.favorites.some(f=>Number(f.id)===Number(b.id))),'favorite-books','emptyFavorites');break;case 'mybooks':renderMyBooks();break;case 'account':renderAccount();break;case 'staff':renderStaff();break;case 'admin':renderAdmin();break;}}
async function favorite(id){if(!isLogged()){openAuth();return}const exists=state.favorites.some(b=>Number(b.id)===Number(id));try{
  await api('/api/favorites/'+id,{method:exists?'DELETE':'PUT'});state.favorites=await api('/api/favorites');renderCurrent();toast(t('saved'))
}catch(e){toast(e.message)}}
async function detail(id){state.chosenBook=id;try{const b=await api('/api/books/'+id);const reviews=await api('/api/books/'+id+'/reviews');$('book-dialog-title').textContent=title(b);
  const available=Number(b.available);$('book-dialog-body').innerHTML=`<div class="detail-grid">${cover(b)}<div><h3>${esc(title(b))}</h3><div class="muted">${esc(b.author)} · ${esc(genreLabel(b.genre))}</div><p>${esc(b.description||'')}</p><div class="detail-info">${esc(t('copies'))}: ${Number(b.copies)} · ${esc(t('available'))}: ${available}</div>
  <div class="detail-actions">${b.online?`<button class="primary-btn" data-action="read" data-id="${b.id}">${esc(t('read'))}</button>`:''}${Number(b.copies)>0?(available>0?`<button class="secondary-btn" data-action="reserve" data-id="${b.id}">${esc(t('reserveNow'))}</button>`:`<button class="secondary-btn" data-action="waitlist" data-id="${b.id}">${esc(t('waitlist'))}</button>`):''}
  <button class="secondary-btn" data-action="favorite" data-id="${b.id}">♡ ${esc(t('favorites'))}</button></div></div></div>
  <div style="margin-top:24px"><h3>${esc(t('reviews'))}</h3>${reviews.map(r=>`<div class="notice"><b>${esc(r.full_name)}</b> · ${'★'.repeat(Math.max(0,Math.min(5,Number(r.rating))))}<p>${esc(r.body||'')}</p></div>`).join('')||empty('emptyTable')}
  ${isLogged()?`<form id="review-form" class="form-stack"><label><span>${esc(t('rating'))}</span><select name="rating">${[5,4,3,2,1].map(n=>`<option value="${n}">${n} ★</option>`).join('')}</select></label><textarea name="body" maxlength="1000" placeholder="${esc(t('reviewPlace'))}"></textarea><button class="secondary-btn">${esc(t('rateSave'))}</button></form>`:''}</div>`;modal('book-dialog');
}catch(e){toast(e.message)}}
async function joinQueue(id){if(!isLogged()){openAuth();return}try{await api('/api/books/'+id+'/waitlist',{method:'POST'});toast(t('done'));dialogClose('book-dialog');await refreshAll()}catch(e){toast(e.message)}}
function openReserve(id){if(!isLogged()){openAuth();return}if(!state.user.phone){dialogClose('book-dialog');go('account');toast(t('contactRequired'));return}
  state.chosenBook=id;const b=state.books.find(x=>Number(x.id)===Number(id));$('reserve-book-title').textContent=b?title(b):'';const input=$('reserve-form').elements.pickupDay;input.min=dateOffset(0);input.max=dateOffset(2);input.value=dateOffset(0);
  dialogClose('book-dialog');modal('reserve-dialog');}
async function reserveBook(e){e.preventDefault();try{const day=e.target.elements.pickupDay.value;let r=await api(`/api/books/${state.chosenBook}/reserve`,{method:'POST',body:{pickupDay:day}});dialogClose('reserve-dialog');toast(`${t('done')} · ${r.qrCode}`);await refreshAll();go('mybooks')}catch(e){toast(e.message)}}
function renderMyBooks(){
  document.querySelectorAll('[data-tab]').forEach(x=>x.classList.toggle('selected',x.dataset.tab===state.tab));
  if(state.tab==='electronic'){
    let ids=state.progress.map(p=>Number(p.id)),items=state.books.filter(b=>ids.includes(Number(b.id)));
    $('mybooks-content').innerHTML=items.length?`<div class="books-grid">${items.map(b=>card(b)).join('')}</div><div class="panel" style="margin-top:20px">${state.progress.map(p=>`<p><b>${esc(title(p))}</b> · ${Number(p.percentage)}% <button class="small-btn" data-action="read" data-id="${Number(p.id)}">${esc(t('read'))}</button></p>`).join('')}</div>`:empty('emptyReading');
  }else{
    const r=state.reservations.map(x=>`<tr><td><b>${esc(title(x))}</b><div class="mini muted">${esc(x.author)}</div></td><td>${esc(statusLabel(x.status))}</td><td>${esc(localDate(x.pickup_day))}</td><td>${x.due_day?esc(localDate(x.due_day)):'—'}</td><td>${['RESERVED','READY'].includes(x.status)?`<button class="small-btn" data-action="qr" data-id="${x.id}">${esc(t('qr'))}</button><button class="small-btn" data-action="cancel-reservation" data-id="${x.id}">${esc(t('cancel'))}</button>`:''}${x.status==='ISSUED'&&!x.renewed?`<button class="small-btn" data-action="renew" data-id="${x.id}">${esc(t('renew'))}</button>`:''}</td></tr>`);
    const queue=state.waitlist.map(x=>`<div class="notice">${esc(title(x))} · ${esc(statusLabel(x.status))} ${x.status==='WAITING'?`<button class="small-btn" data-action="leave-waitlist" data-id="${x.id}">${esc(t('cancel'))}</button>`:''}</div>`).join('')||empty('emptyQueue');
    $('mybooks-content').innerHTML=`<div class="panel table-scroll"><h3>${esc(t('reservations'))}</h3>${r.length?`<table class="data-table"><thead><tr><th>${esc(t('name'))}</th><th>${esc(t('status'))}</th><th>${esc(t('pickupDay'))}</th><th>${esc(t('date'))}</th><th></th></tr></thead><tbody>${r.join('')}</tbody></table>`:empty('emptyReservations')}</div><div class="panel" style="margin-top:18px"><h3>${esc(t('waitlistTitle'))}</h3>${queue}</div>`;
  }
}
function renderAccount(){if(!isLogged())return;const f=$('profile-form');for(const k of ['fullName','email','phone','address'])f.elements[k].value=state.user[k.replace(/[A-Z]/g,ch=>'_'+ch.toLowerCase())]||'';
  $('account-stats').innerHTML=`<div><strong>${state.progress.length}</strong><span>${esc(t('electronic'))}</span></div><div><strong>${state.reservations.length}</strong><span>${esc(t('reservations'))}</span></div>`;
  let quick=$('account-quick-links');if(quick)quick.remove();if(isStaff()){
    const d=document.createElement('div');d.id='account-quick-links';d.className='panel';d.innerHTML=`<h3>${esc(t('staffPanel'))}</h3><button class="secondary-btn" data-nav="staff">${esc(t('staffNav'))}</button> ${isAdmin()?`<button class="secondary-btn" data-nav="admin">${esc(t('adminNav'))}</button>`:''}`;
    document.querySelector('.account-side').append(d);
  }
}
async function saveProfile(e){e.preventDefault();const f=e.target;try{await api('/api/account/profile',{method:'PUT',body:Object.fromEntries(new FormData(f).entries())});await loadUser();toast(t('saved'));renderAccount()}catch(e){toast(e.message)}}
async function changePassword(e){e.preventDefault();try{await api('/api/account/password',{method:'POST',body:Object.fromEntries(new FormData(e.target).entries())});toast(t('passwordChanged'));await loadUser();await refreshAll();openAuth();}catch(e){toast(e.message)}}
async function renderStaff(){if(!isStaff())return;try{
  const rows=await api('/api/staff/reservations');$('staff-reservations').innerHTML=rows.length?`<table class="data-table"><thead><tr><th>${esc(t('name'))}</th><th>${esc(t('users'))}</th><th>${esc(t('status'))}</th><th>${esc(t('pickupDay'))}</th><th>${esc(t('qrCode'))}</th></tr></thead><tbody>${rows.map(r=>`<tr><td>${esc(r.title)}</td><td>${esc(r.full_name)}<small class="muted"> ${esc(r.phone)}</small></td><td><span class="pill">${esc(statusLabel(r.status))}</span></td><td>${esc(localDate(r.pickup_day))}</td><td><code>${esc(r.qr_code)}</code></td></tr>`).join('')}</tbody></table>`:empty('emptyTable');
}catch(e){toast(e.message)}}
// Staff QR scanner: uses native BarcodeDetector when available, otherwise
// decodes camera snapshots via the staff-only ZXing endpoint. Works on HTTPS
// or localhost. File capture remains usable without live camera permission.
let staffQrStream=null,staffQrTimer=null,staffQrBusy=false,staffQrDetector=null;
const validStaffQr=code=>/^PQ-[0-9A-F]{12}$/.test(String(code||'').trim().toUpperCase());
function setStaffQrMessage(html){const el=$('staff-qr-result');if(el)el.innerHTML=html}
function stopStaffQrScan(){
  if(staffQrTimer!==null){clearTimeout(staffQrTimer);staffQrTimer=null}
  if(staffQrStream){staffQrStream.getTracks().forEach(track=>track.stop());staffQrStream=null}
  const video=$('staff-qr-video');if(video){video.pause();video.srcObject=null;video.hidden=true}
  if($('staff-qr-placeholder'))$('staff-qr-placeholder').hidden=false;
  if($('staff-qr-start'))$('staff-qr-start').hidden=false;
  if($('staff-qr-stop'))$('staff-qr-stop').hidden=true;
}
async function verifyStaffQrCode(raw){
  const code=String(raw||'').trim().toUpperCase();
  if(!validStaffQr(code)){setStaffQrMessage(esc(t('qrInvalid')));return false}
  try{
    const r=await api(`/api/staff/qr/lookup?code=${encodeURIComponent(code)}`);
    $('staff-qr-code').value=code;
    $('issue-form').elements.code.value=code;
    $('return-form').elements.code.value=code;
    let hint=['RESERVED','READY'].includes(r.status)?t('qrIssueHint'):r.status==='ISSUED'?t('qrReturnHint'):t('qrOtherStatus');
    let info=`<strong>${esc(t('qrReady'))}: ${esc(code)}</strong><div>${esc(t('qrBook'))}: ${esc(r.title)}<div>${esc(t('qrReader'))}: ${esc(r.full_name)}</div><div>${esc(t('status'))}: ${esc(statusLabel(r.status))}</div><div>${esc(hint)}</div>`;
    setStaffQrMessage(info);
    stopStaffQrScan();
    return true;
  }catch(err){setStaffQrMessage(esc(t('qrUnrecognized'))+' · '+esc(err.message));return false}
}
async function decodeStaffQrImage(blob,{silent=false}={}){
  if(staffQrBusy)return false;
  staffQrBusy=true;
  try{
    const fd=new FormData();fd.append('image',blob,'qr.jpg');
    const result=await api('/api/staff/qr/decode',{method:'POST',body:fd});
    return await verifyStaffQrCode(result.code);
  }catch(err){if(!silent)setStaffQrMessage(esc(err.message.includes('not found')?t('qrNotFound'):err.message));return false}
  finally{staffQrBusy=false}
}
async function staffQrCameraTick(){
  if(!staffQrStream||state.page!=='staff')return stopStaffQrScan();
  const video=$('staff-qr-video');
  if(video && video.readyState>=2 && !staffQrBusy){
    try{
      if(staffQrDetector){
        const results=await staffQrDetector.detect(video);
        if(results.length && await verifyStaffQrCode(results[0].rawValue))return;
      }else{
        const canvas=document.createElement('canvas');
        const scale=Math.min(1,900/video.videoWidth);canvas.width=Math.round(video.videoWidth*scale);canvas.height=Math.round(video.videoHeight*scale);
        if(canvas.width&&canvas.height){
          canvas.getContext('2d',{willReadFrequently:true}).drawImage(video,0,0,canvas.width,canvas.height);
          const blob=await new Promise(resolve=>canvas.toBlob(resolve,'image/jpeg',.84));
          if(blob)await decodeStaffQrImage(blob,{silent:true});
          if(!staffQrStream)return;
        }
      }
    }catch(err){if(staffQrDetector)staffQrDetector=null;console.debug('ParaQ QR frame not decoded',err)}
  }
  if(staffQrStream)staffQrTimer=setTimeout(staffQrCameraTick,staffQrDetector?450:1300);
}
async function startStaffQrScan(){
  stopStaffQrScan();
  if(!navigator.mediaDevices?.getUserMedia){setStaffQrMessage(esc(t('qrCameraError')));return}
  try{
    staffQrStream=await navigator.mediaDevices.getUserMedia({audio:false,video:{facingMode:{ideal:'environment'},width:{ideal:1024},height:{ideal:768}}});
    if(state.page!=='staff')return stopStaffQrScan();
    const video=$('staff-qr-video');video.hidden=false;video.srcObject=staffQrStream;
    $('staff-qr-placeholder').hidden=true;$('staff-qr-start').hidden=true;$('staff-qr-stop').hidden=false;
    staffQrDetector=null;
    if('BarcodeDetector' in window){
      try{staffQrDetector=new BarcodeDetector({formats:['qr_code']})}catch(e){staffQrDetector=null}
    }
    await video.play();staffQrCameraTick();
  }catch(err){stopStaffQrScan();setStaffQrMessage(esc(t('qrCameraError')))}
}
async function staffAction(e,kind){e.preventDefault();try{await api('/api/staff/'+kind,{method:'POST',body:Object.fromEntries(new FormData(e.target).entries())});e.target.reset();toast(t('done'));await refreshAll()}catch(err){toast(err.message)}}
function table(headers,rows){return rows.length?`<div class="panel table-scroll"><table class="data-table"><thead><tr>${headers.map(x=>`<th>${esc(x)}</th>`).join('')}</tr></thead><tbody>${rows.join('')}</tbody></table></div>`:empty('emptyTable')}
function adminForm(book){const b=book||{};const input=(label,name,value,extra='')=>`<label><span>${esc(label)}</span><input name="${name}" value="${esc(value||'')}" ${extra}></label>`;
  return `<div class="panel"><h3>${esc(book?t('edit'):t('addBook'))}</h3><form id="admin-book-form" data-id="${book?book.id:''}" class="inline-form">
  ${input(t('name'),'title',b.title,'required maxlength="220"')}${input(t('author'),'author',b.author,'required maxlength="180"')}
  ${input(t('titleKk'),'titleKk',b.title_kk,'maxlength="220"')}${input(t('titleRu'),'titleRu',b.title_ru,'maxlength="220"')}
  ${input(t('titleEn'),'titleEn',b.title_en,'maxlength="220"')}${input(t('genre'),'genre',b.genre,'maxlength="80"')}
  <label><span>${esc(t('bookLang'))}</span><select name="language">${['kk','ru','en'].map(l=>`<option value="${l}" ${b.language===l?'selected':''}>${l.toUpperCase()}</option>`).join('')}</select></label>
  ${input(t('copies'),'copies',b.copies===undefined?0:b.copies,'type="number" min="0" max="100000" required')}
  <label class="full"><span>${esc(t('coverUrl'))}</span><input name="coverUrl" type="url" value="${esc(b.cover_url||'')}" placeholder="https://..."></label>
  <label class="full"><span>${esc(t('description'))}</span><textarea name="description" maxlength="4000" rows="3">${esc(b.description||'')}</textarea></label>
  <label class="full"><input type="checkbox" name="isPublic" ${b.is_public===false?'':'checked'}> ${esc(t('public'))}</label>
  <div class="full"><button class="primary-btn">${esc(t('save'))}</button> ${book?`<button type="button" class="small-btn" data-action="clear-edit">${esc(t('cancel'))}</button>`:''}</div></form></div>`;
}
async function renderAdmin(){if(!isAdmin())return;
  document.querySelectorAll('[data-admin]').forEach(b=>b.classList.toggle('selected',b.dataset.admin===state.admin));$('admin-content').innerHTML=`<div class="panel">${esc(t('loading'))}</div>`;
  try{let html='';if(state.admin==='dashboard'){
    const x=await api('/api/admin/stats');let cells=[['books','bookCount'],['copies','copies'],['users','users'],['staff','staff'],['activeReservations','activeLoans'],['completedLoans','completedLoans']];
    html=`<div class="metrics">${cells.map(([k,l])=>`<div class="metric"><strong>${Number(x[k])}</strong><small>${esc(t(l))}</small></div>`).join('')}</div><div class="panel"><h3>${esc(t('adminHint'))}</h3><p class="muted">${esc(t('adminPanel'))} · ParaQ</p></div>`;
  }else if(state.admin==='books'){
    state.adminBooks=await api('/api/admin/books');html=adminForm(null)+table([t('name'),t('author'),t('copies'),t('status'),t('apply')],state.adminBooks.map(b=>`<tr><td>${esc(b.title)} ${b.online?'📖':''}</td><td>${esc(b.author)}</td><td>${Number(b.copies)}</td><td>${b.is_public?esc(t('active')):esc(t('archive'))}</td><td><button class="small-btn" data-action="edit-book" data-id="${b.id}">${esc(t('edit'))}</button><button class="small-btn" data-action="upload-book" data-id="${b.id}">${esc(t('upload'))}</button></td></tr>`));
  }else if(state.admin==='users'){
    const xs=await api('/api/admin/users');html=table([t('username'),t('fullName'),t('phone'),t('status'),t('activeLoans'),t('apply')],xs.map(u=>`<tr><td>${esc(u.username)}</td><td>${esc(u.full_name)}<small class="muted"> ${esc(u.email)}</small></td><td>${esc(u.phone)}</td><td>${u.deleted_at?esc(t('deleteAccount')):u.active&&!u.banned_until?esc(t('active')):esc(t('blocked'))}</td><td>${Number(u.issued_count)}</td><td>${u.deleted_at?'—':`<button class="small-btn" data-action="user-ban" data-id="${u.id}">${esc(t('ban'))}</button><button class="small-btn" data-action="user-unban" data-id="${u.id}">${esc(t('unban'))}</button><button class="small-btn warn" data-action="user-delete" data-id="${u.id}">${esc(t('deleteAccount'))}</button>`}</td></tr>`));
  }else if(state.admin==='staff'){
    const xs=await api('/api/admin/staff');html=`<div class="panel"><h3>${esc(t('addStaff'))}</h3><form id="admin-staff-form" class="inline-form"><label><span>${esc(t('username'))}</span><input name="username" required></label><label><span>${esc(t('fullName'))}</span><input name="fullName" required></label><label><span>${esc(t('password'))}</span><input name="password" type="password" minlength="6" required></label><label><span>${esc(t('phone'))}</span><input name="phone"></label><div class="full"><button class="primary-btn">${esc(t('addStaff'))}</button></div></form></div>`+
    table([t('username'),t('fullName'),t('phone'),t('status'),t('apply')],xs.map(u=>`<tr><td>${esc(u.username)}</td><td>${esc(u.full_name)}</td><td>${esc(u.phone)}</td><td>${u.active?esc(t('active')):esc(t('blocked'))}</td><td><button class="small-btn" data-action="staff-ban" data-id="${u.id}">${esc(t('ban'))}</button><button class="small-btn" data-action="staff-unban" data-id="${u.id}">${esc(t('unban'))}</button></td></tr>`));
  }else if(state.admin==='reports'){
    const xs=await api('/api/admin/reports');html=table([t('name'),t('copies'),t('activeLoans'),t('completedLoans'),t('queue')],xs.map(b=>`<tr><td>${esc(b.title)}</td><td>${Number(b.copies)}</td><td>${Number(b.currently_out)}</td><td>${Number(b.returned)}</td><td>${Number(b.waiting)}</td></tr>`));
  }else if(state.admin==='logs'){
    const xs=await api('/api/admin/logs');html=table([t('date'),t('username'),t('status'),t('details')],xs.map(l=>`<tr><td>${esc(String(l.created_at||''))}</td><td>${esc(l.actor||'system')}</td><td>${esc(l.action)}</td><td>${esc(l.detail||'')}</td></tr>`));
  }$('admin-content').innerHTML=html;
 }catch(e){$('admin-content').innerHTML=empty('failed');toast(e.message)}
}
async function adminSaveBook(e){e.preventDefault();const form=e.target;const id=form.dataset.id;let body=Object.fromEntries(new FormData(form).entries());body.copies=Number(body.copies);body.isPublic=!!form.elements.isPublic.checked;
 try{await api(id?'/api/admin/books/'+id:'/api/admin/books',{method:id?'PUT':'POST',body});toast(t('done'));await refreshAll()}catch(e){toast(e.message)}}
async function staffCreate(e){e.preventDefault();try{await api('/api/admin/staff',{method:'POST',body:Object.fromEntries(new FormData(e.target).entries())});toast(t('done'));renderAdmin()}catch(e){toast(e.message)}}
async function uploadBook(id){const inp=document.createElement('input');inp.type='file';inp.accept='.txt,.pdf,.epub';inp.onchange=async()=>{
  if(!inp.files?.length)return;const file=inp.files[0];if(file.size>10_000_000){toast('10 MB');return}
  const form=new FormData();form.append('file',file);try{await api('/api/admin/books/'+id+'/file',{method:'POST',body:form});toast(t('done'));await refreshAll()}catch(e){toast(e.message)}};inp.click()}
async function userCommand(kind,id){const admin=kind.startsWith('staff'),action=kind.endsWith('unban')?'unban':'ban';
  if(!confirm(t('confirm')))return;
  let days=0;if(!admin&&action==='ban'){const d=prompt(`${t('days')}: 0 = ${t('banForever')}`, '7');if(d===null)return;days=Number(d);if(!Number.isInteger(days)||days<0||days>365){toast(t('failed'));return}}
  try{await api(`/api/admin/${admin?'staff':'users'}/${id}/status`,{method:'POST',body:{action,days}});toast(t('done'));renderAdmin()}catch(e){toast(e.message)}}
async function userDelete(id){if(!confirm(t('deleteAccount')+'? '+t('confirm')))return;
  try{await api('/api/admin/users/'+id,{method:'DELETE'});toast(t('done'));renderAdmin()}catch(e){toast(e.message)}}
async function adminEditBook(id){state.admin='books';renderAdmin();const b=state.adminBooks.find(x=>Number(x.id)===Number(id));if(b){$('admin-content').innerHTML=adminForm(b)+$('admin-content').innerHTML;$('admin-book-form').scrollIntoView({behavior:'smooth'})}}
async function readBook(id){if(!isLogged()){openAuth();return}state.readerBook=Number(id);const b=state.books.find(x=>Number(x.id)===Number(id));if(!b){toast(t('fileMissing'));return}dialogClose('book-dialog');
 try{const r=await fetch('/api/books/'+id+'/content',{credentials:'same-origin'});if(!r.ok){const z=await r.json().catch(()=>({}));throw new Error(z.error||t('fileMissing'))}
   const type=r.headers.get('content-type')||'text/plain';const reader=$('reader-content');reader.innerHTML='';reader.classList.remove('pdf-content');
   if(type.includes('pdf')){const blob=await r.blob();const url=URL.createObjectURL(blob);const frame=document.createElement('iframe');frame.title=title(b);frame.src=url;reader.append(frame);reader.classList.add('pdf-content');reader.dataset.blobUrl=url}
   else if(type.includes('epub')){const txt=await api('/api/books/'+id+'/epub-text');reader.textContent=txt}
   else{reader.textContent=await r.text()}
   $('reader-title').textContent=title(b);$('reader-dialog').dataset.readerTheme=state.readerTheme;$('reader-theme').value=state.readerTheme;
   reader.style.setProperty('--reader-size',state.readerSize+'px');state.readerPercent=Number(state.progress.find(p=>Number(p.id)===id)?.percentage||0);
   $('reader-progress').value=state.readerPercent;$('reader-percent').textContent=state.readerPercent+'%';modal('reader-dialog');
 }catch(e){toast(e.message)}}
async function saveProgress(){try{if(!state.readerBook)return;await api('/api/books/'+state.readerBook+'/progress',{method:'PUT',body:{percentage:state.readerPercent}});toast(t('progressSaved'));state.progress=await api('/api/progress')}
 catch(e){toast(e.message)}}
function readerClose(){const url=$('reader-content').dataset.blobUrl;if(url){URL.revokeObjectURL(url);delete $('reader-content').dataset.blobUrl}saveProgress();dialogClose('reader-dialog')}
function openAuth(){state.authTab='login';renderAuth();modal('auth-dialog')}
function renderAuth(){document.querySelectorAll('[data-auth-tab]').forEach(x=>x.classList.toggle('selected',x.dataset.authTab===state.authTab));$('auth-name-row').hidden=state.authTab==='login';$('auth-name-row').querySelector('input').required=state.authTab==='register';$('auth-title').textContent=t(state.authTab);$('auth-submit').textContent=t(state.authTab);const pw=$('auth-form').elements.password;pw.minLength=state.authTab==='register'?6:0;pw.maxLength=72;}
async function submitAuth(e){e.preventDefault();const form=e.target,values=Object.fromEntries(new FormData(form).entries());try{
  if(state.authTab==='register'){
    await api('/api/auth/register',{method:'POST',body:values});toast(t('accountCreated'));state.authTab='login';renderAuth();form.elements.password.value='';return;
  }
  const data=new URLSearchParams({username:values.username,password:values.password});await api('/api/auth/login',{method:'POST',body:data.toString(),headers:{'Content-Type':'application/x-www-form-urlencoded'}});
  await csrf();await loadUser();await refreshAll();dialogClose('auth-dialog');toast(t('done'));go(state.user.role==='ADMIN'?'admin':state.user.role==='LIBRARIAN'?'staff':'home');form.reset();
 }catch(e){toast(e.message==='Invalid username or password'?t('invalidCredentials'):e.message)}}
async function logout(){try{await api('/api/auth/logout',{method:'POST'});state.user={role:'GUEST'};await csrf();await refreshAll();go('home')}catch(e){toast(e.message)}}
async function openNotifications(){if(!isLogged()){openAuth();return}const p=$('notifications-pop');p.hidden=!p.hidden;if(!p.hidden){
  $('notifications-list').innerHTML=state.notices.length?state.notices.map(n=>`<div class="notice ${n.seen?'':'unseen'}">${esc(n['message_'+state.lang]||n.message_kk)}</div>`).join(''):empty('emptyNotifications');}}
async function markRead(){try{await api('/api/notifications/read',{method:'POST'});state.notices=await api('/api/notifications');$('notifications-pop').hidden=true;updateHeader()}catch(e){toast(e.message)}}
async function reviewSubmit(e){e.preventDefault();try{await api(`/api/books/${state.chosenBook}/reviews`,{method:'POST',body:{rating:Number(e.target.elements.rating.value),body:e.target.elements.body.value}});dialogClose('book-dialog');await detail(state.chosenBook)}catch(e){toast(e.message)}}
async function userActions(action,id){try{
  if(action==='favorite')return favorite(id);
  if(action==='detail')return detail(id);
  if(action==='read')return readBook(id);
  if(action==='reserve')return openReserve(id);
  if(action==='waitlist')return joinQueue(id);
  if(action==='qr'){
    const link=`/api/reservations/${id}/qr`;const r=await fetch(link,{credentials:'same-origin'});if(!r.ok)throw new Error(t('failed'));
    const blob=await r.blob();const url=URL.createObjectURL(blob);$('book-dialog-title').textContent=t('qr');
    $('book-dialog-body').innerHTML=`<div style="display:grid;place-items:center;gap:15px;padding:20px"><img id="reservation-qr-image" alt="QR" width="250" height="250"><p>${esc(t('qrCode'))}: ${esc(state.reservations.find(x=>Number(x.id)===id)?.qr_code||'')}</p></div>`;
    $('reservation-qr-image').src=url;$('book-dialog').addEventListener('close',()=>URL.revokeObjectURL(url),{once:true});modal('book-dialog');return;
  }
  if(action==='cancel-reservation'){if(!confirm(t('confirm')))return;await api(`/api/reservations/${id}/cancel`,{method:'POST'});await refreshAll();toast(t('done'))}
  if(action==='renew'){await api(`/api/reservations/${id}/renew`,{method:'POST'});await refreshAll();toast(t('done'))}
  if(action==='leave-waitlist'){await api(`/api/waitlist/${id}`,{method:'DELETE'});await refreshAll();toast(t('done'))}
  if(action==='edit-book'){const b=state.adminBooks.find(x=>Number(x.id)===Number(id));if(b){$('admin-content').innerHTML=adminForm(b)+$('admin-content').innerHTML;$('admin-book-form').scrollIntoView({behavior:'smooth'})}}
  if(action==='clear-edit')renderAdmin();
  if(action==='upload-book')uploadBook(id);
  if(action==='user-delete')userDelete(id);
  if(['user-ban','user-unban','staff-ban','staff-unban'].includes(action))userCommand(action,id);
  if(action==='read-notifications')markRead();
  if(action==='logout')logout();
  if(action==='auth')openAuth();
 }catch(e){toast(e.message)}}
function initEvents(){
  document.addEventListener('click',e=>{
    const close=e.target.closest('[data-close]');if(close){const id=close.dataset.close;if(id==='reader-dialog')readerClose();else dialogClose(id);return}
    const nav=e.target.closest('[data-nav]');if(nav){go(nav.dataset.nav);return}
    const a=e.target.closest('[data-action]');if(a){userActions(a.dataset.action,Number(a.dataset.id||0));return}
    const at=e.target.closest('[data-auth-tab]');if(at){state.authTab=at.dataset.authTab;renderAuth();return}
    const tab=e.target.closest('[data-tab]');if(tab){state.tab=tab.dataset.tab;renderMyBooks();return}
    const ad=e.target.closest('[data-admin]');if(ad){state.admin=ad.dataset.admin;renderAdmin();return}
  });
  $('lang').onchange=e=>setLanguage(e.target.value);$('theme').onchange=e=>setTheme(e.target.value);
  ['search','filter-kind','filter-genre','filter-language'].forEach(id=>$(id).addEventListener(id==='search'?'input':'change',renderCatalog));
  $('filter-toggle').onclick=()=>{$('filters').classList.toggle('expanded')};$('bell').onclick=openNotifications;
  $('auth-form').onsubmit=submitAuth;$('profile-form').onsubmit=saveProfile;$('password-form').onsubmit=changePassword;$('reserve-form').onsubmit=reserveBook;
  $('issue-form').onsubmit=e=>staffAction(e,'issue');$('return-form').onsubmit=e=>staffAction(e,'return');
  $('staff-qr-start').onclick=startStaffQrScan;
  $('staff-qr-stop').onclick=stopStaffQrScan;
  $('staff-qr-check').onclick=()=>verifyStaffQrCode($('staff-qr-code').value);
  $('staff-qr-code').addEventListener('keydown',e=>{if(e.key==='Enter'){e.preventDefault();verifyStaffQrCode(e.target.value)}});
  $('staff-qr-file').onchange=async e=>{
    const file=e.target.files?.[0];if(file){stopStaffQrScan();await decodeStaffQrImage(file)}
    e.target.value='';
  };
  document.addEventListener('visibilitychange',()=>{if(document.hidden)stopStaffQrScan()});
  window.addEventListener('pagehide',stopStaffQrScan);

  document.addEventListener('submit',e=>{if(e.target.id==='review-form'){e.preventDefault();reviewSubmit(e)}if(e.target.id==='admin-book-form'){e.preventDefault();adminSaveBook(e)}if(e.target.id==='admin-staff-form'){e.preventDefault();staffCreate(e)}});
  $('reader-theme').onchange=e=>{state.readerTheme=e.target.value;localStorage.setItem('paraq-reader-theme',state.readerTheme);$('reader-dialog').dataset.readerTheme=state.readerTheme};
  $('reader-smaller').onclick=()=>{state.readerSize=Math.max(14,state.readerSize-2);$('reader-content').style.setProperty('--reader-size',state.readerSize+'px');localStorage.setItem('paraq-reader-size',state.readerSize)};
  $('reader-larger').onclick=()=>{state.readerSize=Math.min(34,state.readerSize+2);$('reader-content').style.setProperty('--reader-size',state.readerSize+'px');localStorage.setItem('paraq-reader-size',state.readerSize)};
  $('reader-progress').oninput=e=>{state.readerPercent=Number(e.target.value);$('reader-percent').textContent=state.readerPercent+'%'};$('reader-save').onclick=saveProgress;
  $('reader-content').addEventListener('scroll',e=>{if($('reader-content').classList.contains('pdf-content'))return;const el=e.target;if(el.scrollHeight<=el.clientHeight)return;
    const percent=Math.min(100,Math.round(100*el.scrollTop/(el.scrollHeight-el.clientHeight)));if(percent>state.readerPercent){state.readerPercent=percent;$('reader-progress').value=percent;$('reader-percent').textContent=percent+'%'}},{passive:true});
  for(const d of document.querySelectorAll('dialog'))d.addEventListener('click',e=>{if(e.target===d){if(d.id==='reader-dialog')readerClose();else d.close()}});
  
}
async function start(){initEvents();initTheme();$('lang').value=state.lang;document.documentElement.lang=state.lang;applyTranslations();
  await csrf();await loadUser();await refreshAll();const hash=location.hash.slice(1);if(['home','catalog','online','favorites','mybooks','account','staff','admin'].includes(hash))go(hash);
}
start();
