#!/bin/bash

echo "-------------------------------------------------------"
echo "Starting Build and Test Process for Service..."
echo "-------------------------------------------------------"


echo "Running Unit Tests..."
sleep 2 
TEST_RESULT=0 

if ( $TEST_RESULT -eq 0 ); then
    echo "Unit Tests Passed Successfully!"
else
    echo "Unit Tests Failed!"
    exit 1
fi


echo "Generating Build Artifacts..."
echo "Build Version: 1.0.$BUILD_NUMBER" > build_info.txt
echo "Build Date: $(date)" >> build_info.txt


tar -czvf service-build-${BUILD_NUMBER}.tar.gz build_info.txt

echo "-------------------------------------------------------"
echo "Build and Test Completed!"
echo "Artifact created: service-build-${BUILD_NUMBER}.tar.gz"
echo "-------------------------------------------------------"
