# YeldosRK
Регистрация пользователя:

Отправьте POST-запрос на http://localhost:8080/api/register с параметрами photoName, username и файлом изображения.

Метод: POST
URL: http://localhost:8080/api/register
Тело запроса (form-data):

    photoName: my-photo
    username: my-user
    file: выберите изображение

Авторизация пользователя:

Отправьте POST-запрос на http://localhost:8080/api/login с параметром photoName.

Метод: POST
URL: http://localhost:8080/api/login
Тело запроса (JSON):

{
  "photoName": "my-photo"
}
