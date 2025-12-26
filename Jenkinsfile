#!/bin/bash

echo "-------------------------------------------------------"
echo "Starting Build and Test Process for Service..."
echo "-------------------------------------------------------"

//1. Simulate Unit Tests
echo "Running Unit Tests..."
sleep 2 //Simulating processing time
TEST_RESULT=0 // 0 means success

if [ $TEST_RESULT -eq 0 ]; then
    echo "Unit Tests Passed Successfully!"
else
    echo "Unit Tests Failed!"
    exit 1
fi

//2. Create Fake Build Artifacts
echo "Generating Build Artifacts..."
echo "Build Version: 1.0.$BUILD_NUMBER" > build_info.txt
echo "Build Date: $(date)" >> build_info.txt

//Create the .tar.gz file in the Jenkins workspace
tar -czvf service-build-${BUILD_NUMBER}.tar.gz build_info.txt

echo "-------------------------------------------------------"
echo "Build and Test Completed!"
echo "Artifact created: service-build-${BUILD_NUMBER}.tar.gz"
echo "-------------------------------------------------------"
