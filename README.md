### Для запуска тестов необходимо:

1. Запустить контейнеры командой `docker-compose up`.
2. Запустить приложение командой `java -jar artifacts/aqa-shop.jar`.
3. Запустить авто тесты командой `./gradlew clean test`.
4. Для создания отчета Allure запустить команду `./gradlew allureServe`.





[![Build status](https://ci.appveyor.com/api/projects/status/ab4gv9dl6464gt48?svg=true)](https://ci.appveyor.com/project/NetologyAlex/qadiplome)
