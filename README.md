# GithubAPI

### Export package:

Çekilen verinin dosyaya yazılması `FileExporter` interface'i üzerinden yapılmaktadır.
Çalışmada istenen format `TextFileExporter` class'ı içerisinde implement edilmiştir.
Farklı bir formatta export edilmek istenirse (örneğin xml) `FileExporter` interface'ini 
implement eden yeni bir class yazılması yeterli olacaktır.

### Model package:

GitHub API'dan çekilen veriyi temsil etmek için gerekli modelleri içerir.

### Service package:

GitHub API erişimi için gerekli metotlar `GitHubClient` interface'i üzerinde tanımlanmıştır.
Bu interface için Spring RestTemplate ve Retrofit http clientlarını kullanan iki farklı implementasyon bulunmaktadır.

Her iki client için, request'e header ekleyen, birer tane interceptor oluşturulmuştur.
Header eklenmezse github'ın rate limitine takıldığından 403 hatası almaktadır.
Bu yüzden interceptor da Authorization yapılmıştır. (Authorization olmadan data çekme
sayısı 60 iken Authorization ile daha fazla çekme imkanı sunmaktadır.)

### GithubManager:

`GithubManager` uygulamanın business katmanıdır. 3 önemli method'a sahiptir.
- `getRepositories`: Commentli satırlarda datayı çekip forkQuantity'e göre sıralama mevcuttur.
Fakat `search` endpointinde direkt sıralama fonksiyonu bulunduğundan bu tercih edilmiştir.
Böylece gereksiz data çekip sıralama işleminden kurtulmuş olunur.

- `getUsers`: Organizasyon ve repository ismine göre user servisini çağırır. User bilgilerini döner.

- `updateContributor`: Her bir user için servis çağrısı yaparak location ve company bilgilerini doldurur.

### Kullanım:

Uygulamayı kullanmak için Application sınıfını çalıştırmak yeterlidir.
Burada data çekmek için kullanılacak client seçilebilir.

### Test:
Son olarak,
`SpringGitHubClientTest` class'ı `SpringGitHubClient` classındaki metotları test eder.
Dataları doğru çekip çekmediğini kontrol eder.
