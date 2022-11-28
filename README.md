### Для запуска тестов необходимо:

1. Запустить контейнеры командой `docker-compose up`.
2. Запустить приложение командой `java -jar artifacts/aqa-shop.jar`.
3. Запустить авто тесты командой `./gradlew clean test`.
4. Для создания отчета Allure запустить команду `./gradlew allureServe`.

### Переключение СУБД

### **1. MySQL**
https://github.com/NetologyAlex/QADiplome/blob/3bc0ed8d2ed05a7a1cf3913a991cd8a88449a4d8/application.properties#L9

### **2. PostgreSQL**
https://github.com/NetologyAlex/QADiplome/blob/3bc0ed8d2ed05a7a1cf3913a991cd8a88449a4d8/application.properties#L4




[![Build status](https://ci.appveyor.com/api/projects/status/ab4gv9dl6464gt48?svg=true)](https://ci.appveyor.com/project/NetologyAlex/qadiplome)
