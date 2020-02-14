package com.nhatminh.example.architecture.demoarchitecture.search.idlingresource;


import androidx.test.espresso.IdlingResource;

public class CountingIdlingResource implements IdlingResource {

    private String resourceName;
    private static int count;
    private ResourceCallback resourceCallback;

    CountingIdlingResource(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getName() {
        return resourceName;
    }

    @Override
    public boolean isIdleNow() {
        return count == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    void increment() {
        count++;
    }

    public void decrement() {
        count--;

        if(count==0) {
            if(resourceCallback!=null) {
                resourceCallback.onTransitionToIdle();
            }
        }

        if(count<0) {
            throw new IllegalArgumentException("Counter corrupted!");
        }
    }

}
