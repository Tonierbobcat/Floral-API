package com.loficostudios.floralcraftapi.profile.components.resources;

public class ResourceInstance {

    private final Resource resource;
    private double current;
    public ResourceInstance(Resource type, double current) {
        this.resource = type;
        this.current = current;
    }

    public Resource getResource() {
        return resource;
    }

    public void setCurrent(double v) {
        this.current = v;
    }

    public double getCurrent() {
        return this.current;
    }

    @Override
    public String toString() {
        return "ResourceInstance{" +
                "resource=" + resource.name() +
                ", current=" + current +
                '}';
    }
}
