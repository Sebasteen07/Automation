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
  build:
    on-failure: ABORT
    commands:
      - cd ${execution_folder}
      # - xvfb-run ${maven_command}
