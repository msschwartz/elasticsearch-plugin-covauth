== Covisint Authorization Elasticsearch Plugin ==
This is just a draft version.

==== Testing ====
The _package-and-start-server.sh_ script can be used to package the plugin and fire up an 
elasticsearch cluster with this plugin installed. Before running the script, download v1.5.2 and 
extract contents into a directory named _elasticsearch_ at the root of this project. 

==== Configuration ====
This plugin can be configured directly from within elasticsearch.yml

E.g.

```yaml
covauth:
    authServiceBaseUrl: https://api.domain.com/oauth/v1
	applicationId: xxxxxx
	clientId: xxxxxx
	clientSecret: xxxxxx
```
