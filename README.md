# Akrep İndirici v2

Native Android (Kotlin + Jetpack Compose) reklamsız video düzenleme/yönetim uygulaması.

**Kurucu:** Nebi Özkan

## Mimari

- **UI:** Jetpack Compose, Material3, tek-activity + Navigation-Compose
- **DI:** Hilt
- **Yerel veri:** Room (işlenen medya geçmişi) + DataStore (ayarlar/tema tercihi)
- **Güvenlik:** AndroidX Biometric (parmak izi ile uygulama kilidi)
- **Medya:** Media3 (ExoPlayer) — yerel video oynatma için

## Özellikler

- 4 sekme: Anasayfa, TikTok, Instagram, Ayarlar
- Cihazdan video içe aktarma (sistem medya seçici ile)
- Izgara (grid) tabanlı içe aktarma geçmişi
- 5 hazır tema paleti: Siyah İnci, Gece Mavisi, Zümrüt, Yakut, Nebi Özkan Özel
- Genel Siyah/Beyaz mod anahtarı
- Parmak izi ile uygulama kilidi (Ayarlar'dan açılıp kapatılabilir)

## ⚠️ Kapsam Dışı Bırakılanlar

Bu build **bilinçli olarak** şunları içermez:

- X/Twitter, TikTok, Instagram gibi platformlardan otomatik video çekme (scraping)
- Bot/Captcha doğrulama aşma katmanları
- Filigran (watermark) veya içerik sahibi kullanıcı adı/metadata silme

Bu bileşenler ilgili platformların Kullanım Şartları'nı ihlal eder ve içerik
üreticilerinin atıf haklarını zedeler. Uygulama bunun yerine **kullanıcının
kendi cihazından seçtiği videoları** işler. Platformlardan içerik çekmek
isteniyorsa, bunun yasal yolu ilgili platformun **resmi API'sini** kullanmak
ve geliştirici sözleşmesine uymaktır.

## Kurulum

```bash
git clone https://github.com/<kullanici-adiniz>/akrepindirici-v2.git
cd akrepindirici-v2
```

Android Studio (Ladybug veya üzeri) ile açın, Gradle senkronizasyonunun
bitmesini bekleyin, ardından `app` modülünü bir cihaz/emülatörde çalıştırın.

### Komut satırından derleme

```bash
./gradlew assembleDebug
```

APK çıktısı: `app/build/outputs/apk/debug/app-debug.apk`

## Güvenlik Notu

`gradle.properties`, `local.properties` veya herhangi bir `*token*` /
`*.keystore` dosyasını **asla** commit etmeyin — `.gitignore` bunları zaten
hariç tutuyor. İmzalama anahtarlarını ve API token'larını ortam değişkenleri
veya CI secrets üzerinden yönetin.
