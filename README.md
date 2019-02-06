# News

* [Что сделано](#about)
* [Что делает приложение](#what_for)
* [Как это работает](#how_it_is_work)
* [Использованные библиотеки](#library)
* [Screenshots](#screenshots)

### <a name="about"></a>Что сделано:
Приложение - RSS-агрегатор создано используя паттерн **MVP**, применялись популярные [библиотеки](#library) для android. Оформление соответствует принципам **Material Design**, шрифты брались из com.android.support:design:28.0.0, цвета - из Material Design color palettes.
***
### <a name="what_for"></a>Что делает приложение:
Приложение дает возможность пользователю посмотреть новостные ленты из пяти источников (источник выбирается пользователем в выдвижной боковой панели - Navigation Drawer): ngs.ru, meduza.io, yandex.ru, lenta.ru, rbc.ru; новости кликабельны и открываются для полного просмотра.

### <a name="how_it_is_work"></a>Как это работает:
Navigator view, выполнен в отдельном фрагменте, отображает  список источников новостных лент (rss-лент), при клике на название в другом фрагменте открывается соответствующая новостная лента (скачивается с соответствующего сайта), toolbar меняет цвет и на нем отображается источник новостей (ссылки на сайты хранятся в build.gradle `android {...  defaultConfig { buildConfigField "String", ... }...}` ). Новостная лента - это recycler view, в котором item - это card view, содержащий: картинку (либо placeholder), заголовок новости, краткое содержание и дату размещения на сайте источника. При клике на новость в ленте фрагмент с rss-лентой заменяется на фрагмент с новостью. Если новость с сайта meduza.io, то фрагмент с новостью - простой фрагмент с разметкой для текстов (заголовок, краткое содержание, полное содержание новости и время публикования) и картинок, если новость с остальных источников - то фрагмент с новостью - это web view, в который загружается соответствующая новость. Back stack содержит историю "в глубину" и очищается при выборе в navigator view другого новостного источника. Одна activity и четыре фрагмента: NewsSourceNavigationViewFragment, RssFragment, WebViewFragment, NewsFromJsonFragment.

### <a name="library"></a> Использованные библиотеки:
* **Butter Knife**.
* **Lombok** - использовал при создании классов-моделей.
* **Picasso** - с помощью этой библиотеки загружал картинки в NewsFromJsonFragment.
* Для работы с форматом json пакет com.google.code.**gson**.
* **Retrofit 2** вместе с ***converter-simplexml*** и ***okhttp3*** для взятия rss-ленты в xml формате, парсинга и отображении в recycler view в виде ленты новостей
* **Retrofit 2** вместе с ***RxJava***, ***adapter-rxjava2*** и ***converter-gson*** - для взятия новости в формате json (с сайта meduza.io), парсинга и отображения новости в NewsFromJsonFragment.
* **Jsoup** - для взятия текста с HTML-страницы, чтобы разместить как обычный текст в NewsFromJsonFragment.

### <a name="screenshots"></a>Screenshots:

![Start screen](https://github.com/MyAndroidProjects/News/blob/master/Screenshots/start_screen.png)
![Open navigation drawer](https://github.com/MyAndroidProjects/News/blob/master/Screenshots/rbc_open_nav_drawer.png)
![RSS from meduza.io](https://github.com/MyAndroidProjects/News/blob/master/Screenshots/meduza_rss.png)
![news](https://github.com/MyAndroidProjects/News/blob/master/Screenshots/meduza_news.png)
![RSS from lenta.ru](https://github.com/MyAndroidProjects/News/blob/master/Screenshots/lenta_rss.png)
