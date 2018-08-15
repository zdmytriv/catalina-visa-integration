# Usage

## Build
```
maven clean package
```

## Execute

### UrlConnection example

```
java -cp target/visa-integration-1.0-SNAPSHOT.jar \
	-Dvgs.proxy.user=<vgs-proxy-user> \
	-Dvgs.proxy.password=<vgs-proxy-password> \
	-Dvgs.proxy.host=your-tenant.sandbox.verygoodproxy.com \
	-Dvisa.user=<visa-user> \
	-Dvisa.password=<visa-password> \
	com.verygood.security.UrlConnectionExample
```

### Apache HttpClient example

```
java -cp target/visa-integration-1.0-SNAPSHOT.jar \
	-Dvgs.proxy.user=<vgs-proxy-user> \
	-Dvgs.proxy.password=<vgs-proxy-password> \
	-Dvgs.proxy.host=your-tenant.sandbox.verygoodproxy.com \
	-Dvisa.user=<visa-user> \
	-Dvisa.password=<visa-password> \
	com.verygood.security.HttpClientExample
```
