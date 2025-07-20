Избегание магических чисел
https://github.com/KomX512/SOLID_SHOP_HW/blob/0ff11cf2adb3276a75c53b3a12b13bc456df170c/src/main/java/Main.java#L17
Такое решение в кейс даёт возможность дальнейшего расширения программы и перестановки пунктов.

DRY
https://github.com/KomX512/SOLID_SHOP_HW/blob/0ff11cf2adb3276a75c53b3a12b13bc456df170c/src/main/java/Service/Useful.java#L9
https://github.com/KomX512/SOLID_SHOP_HW/blob/0ff11cf2adb3276a75c53b3a12b13bc456df170c/src/main/java/Trade/Order.java#L140

методы содержат куски кода которые встречаются несколько раз.
метод добавления номеклатуры addGoods можно потом использовать для изменения товарного состава

некоторые общие моменты вообще выделены в Класс Useful. Я его использовал толкьо для хранения таких обращений. Объекты этого класса не создаются

Классы созданы под свои функции (Single-responsibility principle)
например класс Depo
https://github.com/KomX512/SOLID_SHOP_HW/blob/0ff11cf2adb3276a75c53b3a12b13bc456df170c/src/main/java/Trade/Depo.java#L5

хранить листы со списком номенклатуры, списком заказов и рейтингом
при передаче между методами облегчает ориентирование нужным спсикам объектов
и позволяет в дальнейшем расширить возможности программы
нужный лист добавляется в этот класс и не потребует переписывания уже готовых методов

(Open-Closed principle)
Класс документы создан для возможности дальнейшего расширения, добавления новых типов документов.
Пока создан только документ Заказ (class Order). 
 https://github.com/KomX512/SOLID_SHOP_HW/blob/e95aae8373333756d02335685d06e0542351ea86/src/main/java/Trade/Documents.java#L1

Заодно позволяет внести принцип подстановки Барбары Лисков.(Liskov Substitution Principle)
https://github.com/KomX512/SOLID_SHOP_HW/blob/e95aae8373333756d02335685d06e0542351ea86/src/main/java/Trade/Documents.java#L19
поиск по номеру будет доступен в потом созданных классах, наследниках Documents

(Interface Segregation Principle)
для классов которые должны иметь возможнось выводиться на печать 
добавлен интерфейс Print
https://github.com/KomX512/SOLID_SHOP_HW/blob/76c12c5ef06106b8bce1c3b6fa504ba493ffba5a/src/main/java/Trade/Print.java#L1
https://github.com/KomX512/SOLID_SHOP_HW/blob/76c12c5ef06106b8bce1c3b6fa504ba493ffba5a/src/main/java/Trade/Nomenclature.java#L8
https://github.com/KomX512/SOLID_SHOP_HW/blob/76c12c5ef06106b8bce1c3b6fa504ba493ffba5a/src/main/java/Trade/Order.java#L10
