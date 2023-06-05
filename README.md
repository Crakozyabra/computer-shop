### Java и Spring Boot

#### Цель:
Создать небольшое приложение на базе Spring Boot
#### Задание:
Магазин, торгующий компьютерами и комплектующими со следующим типом товаров:

- Настольные компьютеры
- Ноутбуки
- Мониторы
- Жесткие диски

Каждый товар имеет следующие свойства:

- номер серии
- производитель
- цена
- количество единиц продукции на складе

Дополнительные свойства:

- Настольные компьютеры имеют форм-фактор: десктопы, неттопы, моноблоки
- Ноутбуки подразделяются по размеру: 13, 14, 15, 17 дюймовые
- Мониторы имеют диагональ
- Жесткие диски имеют объем

Необходимо реализовать back-end приложение, которое имеет RESTful HTTP методы,
выполняющие:

1. Добавление товара
2. Редактирование товара
3. Просмотр всех существующих товаров по типу
4. Просмотр товара по идентификатору

В качестве базы данных использовать in memory database, например H2

Для проверки результата, присылайте ссылку на репозиторий на GitHub, в котором
размещен код проекта. В README.md файле репозитория должна быть подробная
инструкция по запуску приложения.

### Инструкция по запуску приложения:

#### Средствами Intelij idea (работает для ultimate edition версии 2023.1.2):

1. Открыть Intelj idea. Если открыт проект, закрыть (File -> Close Project)
2. В появившемся окне нажать кнопку "Get from VCS"
3. В появившемся окне выбрать систему контроля версий "Git", указать URL https://github.com/Crakozyabra/computer-shop и
директорию для распаковки и нажать кнопку "Clone"
4. В открывшемся проекте нажать зеленую стрелочку или комбинацию клавиш Shift + F10
5. Можно тектировать REST API через Swagger UI в браузере по URL http://localhost:8080/swagger-ui/index.html

#### Средствами консоли (должны быть установлены git, openjdk-17, maven)

1. Перейти в консоли в папку в которой будет находиться проект (например командой cd)
2. Выполнить в консоли: git clone https://github.com/Crakozyabra/computer-shop
3. Выполнить в консоли: cd ./computer-shop
4. Выполнить в консоли: mvn spring-boot:run
5. Можно тестировать REST API через Swagger UI в браузере по URL http://localhost:8080/swagger-ui/index.html

### Примечания:

1. Отдельно поднимается TCP H2 сервер, чтобы одновременно при запуске приложения можно было просматривать базу данных
в Intelij idea с URL jdbc:h2:tcp://localhost:9092/mem:cs
2. Тестами покрыты только основные сценарии ProductController
3. Ссылка на codacy: https://app.codacy.com/gh/Crakozyabra/computer-shop/dashboard