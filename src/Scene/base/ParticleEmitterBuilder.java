package Scene.base;

import org.lwjgl.util.vector.Vector3f;

public class ParticleEmitterBuilder {

    private Vector3f location = new Vector3f(0, 0, 0);
    private float spawningRate = 3;
    private int particleLifeTime = 300;
    private Vector3f gravity = new Vector3f(0, -0.0003f, 0);
    private boolean enable3D = false;
    private Vector3f initialVelocity = new Vector3f(0, 0, 0);
    private float velocityModifier = 1.0f;

    public ParticleEmitterBuilder setLocation(Vector3f location) {
        this.location = location;
        return this;
    }

    public ParticleEmitterBuilder setVelocityModifier(float velocityModifier) {
        this.velocityModifier = velocityModifier;
        return this;
    }

    public ParticleEmitterBuilder setEnable3D(boolean enable3D) {
        this.enable3D = enable3D;
        return this;
    }

    public ParticleEmitterBuilder setSpawningRate(float spawningRate) {
        this.spawningRate = spawningRate;
        return this;
    }

    public ParticleEmitterBuilder setParticleLifeTime(int particleLifeTime) {
        this.particleLifeTime = particleLifeTime;
        return this;
    }

    public ParticleEmitterBuilder setGravity(Vector3f gravity) {
        this.gravity = gravity;
        return this;
    }

    public ParticleEmitterBuilder setInitialVelocity(Vector3f initialVelocity) {
        this.initialVelocity = initialVelocity;
        return this;
    }

    public ParticleEmitter createParticleEmitter() {
        return new ParticleEmitter(location, spawningRate, particleLifeTime, gravity, enable3D, initialVelocity,
                velocityModifier);
    }
}