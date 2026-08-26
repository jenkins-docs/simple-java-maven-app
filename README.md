# Simple Java Maven App

## Projenin ne yaptığı

Bu proje, çalıştırıldığında ekrana `Hello World!` yazan basit bir Java uygulamasıdır.
Yeni başlayanların küçük bir Java projesinin nasıl düzenlendiğini görmesi için hazırlanmıştır.

## Proje yapısı

- `src/main/java`: Uygulamanın Java kodunu içerir.
- `src/test/java`: Uygulamanın testlerini içerir.
- `pom.xml`: Projenin Maven ayarlarını içerir.
- `jenkins`: Jenkins ile otomatik derleme ve test için örnek dosyaları içerir.

## Uygulamayı çalıştırma

Bilgisayarınızda Java 21 veya daha yeni bir sürüm ile Maven 3.9.9 veya daha yeni bir sürüm kurulu olmalıdır.

Proje klasöründe önce uygulamayı hazırlayın:

```sh
mvn clean package
```

Ardından uygulamayı çalıştırın:

```sh
java -jar target/my-app-1.0-SNAPSHOT.jar
```

Terminalde `Hello World!` mesajını görmelisiniz.

## Testleri çalıştırma

Proje klasöründe şu komutu kullanın:

```sh
mvn test
```

Testler başarılıysa Maven işlemin başarıyla tamamlandığını bildirir.
