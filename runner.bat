@REM #!/bin/bat

@REM #-------------------------------------------------------------------
@REM #  This script expects the following environment variables
@REM #     HUB_HOST
@REM #     BROWSER
@REM #     THREAD_COUNT
@REM #     TEST_SUITE
@REM #-------------------------------------------------------------------

@REM # Let's print what we have received
@REM echo "-------------------------------------------"
@REM echo "HUB_HOST      : ${HUB_HOST:-hub}"
@REM echo "BROWSER       : ${BROWSER:-chrome}"
@REM echo "THREAD_COUNT  : ${THREAD_COUNT:-1}"
@REM echo "TEST_SUITE    : ${TEST_SUITE}"
@REM echo "-------------------------------------------"

@REM # Do not start the tests immediately. Hub has to be ready with browser nodes
@REM echo "Checking if hub is ready..!"
@REM count=0
@REM while [ "$( curl -s http://${HUB_HOST:-hub}:4444/status | jq -r .value.ready )" != "true" ]
@REM do
@REM   count=$((count+1))
@REM   echo "Attempt: ${count}"
@REM   if [ "$count" -ge 30 ]
@REM   then
@REM       echo "**** HUB IS NOT READY WITHIN 30 SECONDS ****"
@REM       exit 1
@REM   fi
@REM   sleep 1
@REM done

@REM # At this point, selenium grid should be up!
@REM echo "Selenium Grid is up and running. Running the test...."

@REM # Start the java command
@REM java -cp 'libs/*' \
@REM      -Dselenium.grid.enabled=true \
@REM      -Dselenium.grid.hubHost="${HUB_HOST:-hub}" \
@REM      -Dbrowser="${BROWSER:-chrome}" \
@REM      org.testng.TestNG \
@REM      -threadcount "${THREAD_COUNT:-1}" \
@REM      test-suites/"${TEST_SUITE}"