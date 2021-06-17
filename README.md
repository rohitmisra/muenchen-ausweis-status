# muenchen-ausweis-status

Bot to check if your applied Ausweis/Passport/Residence permit in Munich City is available for pickup and notify on Slack

## Requirements

- Docker

## Installation

1. Download the docker-compose file:
    ```
    $wget https://github.com/rohitmisra/muenchen-ausweis-status/blob/master/docs/install/docker/docker-compose.yml 
    ```

2. Create an application config file in ./app-config/application.yml.
   (The template exists in the docs/install/docker directory) in this repo.
   ```
   $mkdir app-config && cd app-config
   $wget https://raw.githubusercontent.com/rohitmisra/muenchen-ausweis-status/master/docs/install/docker/app-config/application.yaml.template
   ```

3. Add documents to watch for
   
   ```
   - tag: passport-rohit
     holder: Rohit
     document_id: <document-id>
     document_type: passport
   ```

4. Add the credentials for the Slack Bot
   - Create a slack bot
   - copy the bot name, auth token
   - create a channel on Slack
   - copy the id of the channel (not name!!)
5. The final config file should look like the template provided:
   
## Run
```
In the root directory:
$docker-compose up -d
```

 