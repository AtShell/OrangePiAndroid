# OrangePiAndroid

Разработана система управления светодиодами.

Управление системой осуществляется с помощью мобильного приложения на Android

Псевдоним | ФИО | Группа
--- | --- | ---
AtShell | Уржумов Вячеслав Игоревич | РИС-20-2б


<h2>Настройка автозапуска</h2>
В директории /etc/systemd/system создаём файл led_control.service со следующим скриптом:


```
[Unit]
Description=Led_service
After=multi-user.target

[Service]
Type=idle
ExecStart=uvicorn main:app --host 0.0.0.0 --port 8000 --reload
WorkingDirectory=/home/schtinguerch/Documents/gtest/Controller
User=root

[Install]
WantedBy=multi-user.target
```
После чего выполняем следующие команды:

```
sudo systemctl daemon-reload
sudo systemctl enable led_control.service
sudo systemctl start led_control.service 
```

