call gradlew eclipse

del SciFiClient.launch
del SciFiServer.launch

call gradlew genEclipseRuns

ren runClient.launch SciFiClient.launch
ren runServer.launch SciFiServer.launch

pause