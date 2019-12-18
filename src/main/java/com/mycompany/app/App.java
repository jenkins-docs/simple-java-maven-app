package com.mycompany.app;

/**
 * Hello world!
 */
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.codeguruprofilerjavaagent.Profiler;
public class App
{

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
	Profiler.builder().profilingGroupName("sample-maven")
        .awsCredentialsProvider(DefaultCredentialsProvider.create())
        .build()
        .start();

        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
