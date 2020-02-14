package com.nhatminh.example.architecture.demoarchitecture.search.idlingresource;


import androidx.test.espresso.IdlingResource;

public class EspressoIdlingResource {

    private static final String RESOURCE = "IDLINGRESOURCE";

    private static CountingIdlingResource countingIdlingResource =
            new CountingIdlingResource(RESOURCE);

    public static void increment() {
        countingIdlingResource.increment();
    }

    public static void decrement() {
        countingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }

}
