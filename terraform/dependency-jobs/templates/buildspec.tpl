version: 0.2

phases:
  install:
    runtime-versions:
      java: corretto8
  pre_build:
    on-failure: ABORT
    commands:
      - export CODEARTIFACT_AUTH_TOKEN=`aws codeartifact get-authorization-token --domain ${domain} --domain-owner ${owner} --query authorizationToken --output text`
      - |
         if [ -z "$CODEARTIFACT_AUTH_TOKEN" ] ; then
           echo "Unable to generate CodeArtifact auth token ... exiting"
           exit 1;
         fi
      - echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd"> 
                <localRepository/> 
                <interactiveMode/> 
                <offline/> 
                <pluginGroups/> 
                <server> 
                    <id>nextgen-pxp-mf-build--pxp-mf</id> 
                    <username>aws</username> 
                    <password>$${env.CODEARTIFACT_AUTH_TOKEN}</password> 
                </server> 
                <mirrors/> 
                <proxies/> 
                <profiles/> 
                <activeProfiles/> 
              </settings> 
          ' >> ~/.m2/settings.xml
      - echo '********************************************************************************' && echo 'Showing contents of settings.xml' && echo '********************************************************************************' && cat ~/.m2/settings.xml
  build:
    on-failure: ABORT
    commands:
      - cd ${execution_folder}
      - ${maven_command}
