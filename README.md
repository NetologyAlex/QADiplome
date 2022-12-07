### Запуск тестового приложения:  
1. Запустить MySQL, PostgreSQL, NodeJS через терминал командой:  
``docker-compose up``
2. В новой вкладке терминала запустить тестируемое приложение:  
Для MySQL:  
``java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar``  
Для PostgreSQL:  
``java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar``
3. Убедиться в готовности системы. Приложение должно быть доступно по адресу:  
``http://localhost:8080/``

### Запуск тестов:  
В новой вкладке терминала запустить тесты:

1. Для MySQL:  
``gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app``
2. Для PostgreSQL:  
``gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app``  

### Перезапуск тестов и приложения:  
Для остановки приложения в окне терминала нужно ввести команду ``Ctrl+С`` и повторить необходимые действия из предыдущих разделов.  

### Формирование отчёта о тестировании:  
Предусмотрено формирование отчётности через Allure. Для этого в новой вкладке терминала вводим команду  
``gradlew allureServe``



[![Build status](https://ci.appveyor.com/api/projects/status/ab4gv9dl6464gt48?svg=true)](https://ci.appveyor.com/project/NetologyAlex/qadiplome)
