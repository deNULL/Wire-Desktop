Wire
============

Кроссплатформенный клиент на Java для мобильного мессенджера Telegram (http://telegram.org/).

В Eclipse можно импортировать как существующий проект.

/src/ru/denull/wire/Main.java - «точка входа» в клиент

/gen/Main.java - генератор классов по TL-манифесту (из /types.txt в /src/tl)


/src/ru/denull/wire - интерфейс

/src/ru/denull/wire/model - модель (менеджеры данных)

/src/ru/denull/wire/mtproto - работа с протоколом MTProto

/src/tl - работа с TL (упаковка/распаковка объектов в/из ByteBuffer)
