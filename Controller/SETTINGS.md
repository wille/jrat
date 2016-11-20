# Settings

Settings are saved in ```./files/settings.json```

Do not modify these if you don't know what you're doing.


### Default configuration

```json
{
  "hosts":[],
  "build_id":"Client",
  "build_password":"swCWCp06FQeUhSp+ToWo7Q==",
  "reconnect_rate":15,
  "use_tray_icon":true,
  "track_statistics":true,
  "installation_name":"File",
  "start_remote_screen_directly":false,
  "request_dialog":true,
  "maximum_connections":-1,
  "use_country_db":true,
  "transfer_plugins_on_connect":false,
  "row_height":30,
  "proxy":{
    "port":9050,
    "enabled":false,
    "host":"127.0.0.1",
    "socks":true
  },
  "columns":{
    "Operating System":true,
    "RAM":true,
    "User@Host":true,
    "IP/Port":true,
    "CPU":false,
    "Country":true,
    "Desktop Environment":false,
    "Status":true,
    "Network Usage":false,
    "Cores":false,
    "ID":true,
    "Headless":false,
    "Local Address":true,
    "Ping":true,
    "Version":true
  },
  "sockets":{
    "Socket2906":{
      "port":1336,
      "pass":"swCWCp06FQeUhSp+ToWo7Q==",
      "type":0,
      "timeout":15000
    }
  },
  "theme":null,
  "eula_shown":false,
  "state_delay":2000
}
```

You can generate a new config file using the argument ```--dump-default-config <file>``` when running the Controller
