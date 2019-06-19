# This example is to show the PubSub watchdog reporting network failure of staying connected to Redis to listen for Publications on the Topic
To run the springboot application have gradle installed and java 8 also installed.
To test out the and see the Watchdog and Reconnect happen run following gradle
./gradlew build bootRun

You can make the following curl command curl --request GET --url http://localhost:8080/ping to place a message on the 
topic "topic:azure:ID" for redis which the RedisService that extends RedisPubSubAdapter that is listening to the Topic 
that meets topic:azure:* criteria. 

Please wait between 10 to 20 minutes for the watchdog to report the reconnect do to losing the connection.

