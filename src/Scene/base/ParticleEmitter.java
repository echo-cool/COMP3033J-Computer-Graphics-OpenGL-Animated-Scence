package Scene.base;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class ParticleEmitter {

    private static final Random randomGenerator = new Random();
    private final List<Particle> particles;
    private final Vector3f location;
    private final float spawningRate;
    private final int particleLifeTime;
    private final Vector3f gravity;
    private final Vector3f initialVelocity;
    private final boolean enable3D;
    private final float velocityModifier;
    public Vector3f colorVec = new Vector3f(0, 0.2f, 1f);

    public ParticleEmitter(Vector3f location, float spawningRate, int particleLifeTime, Vector3f gravity,
                           boolean enable3D, Vector3f initialVelocity, float velocityModifier) {
        this.location = location;
        this.spawningRate = spawningRate;
        this.particleLifeTime = particleLifeTime;
        this.gravity = gravity;
        this.enable3D = enable3D;
        this.particles = new ArrayList<Particle>((int) spawningRate * particleLifeTime);
        this.initialVelocity = initialVelocity;
        this.velocityModifier = velocityModifier;
    }

    private Particle generateNewParticle(int dx, int dy) {
        Vector3f particleLocation = new Vector3f(location);
        Vector3f particleVelocity = new Vector3f();
        float randomX = (float) randomGenerator.nextDouble() - 0.5f;
        float randomY = (float) randomGenerator.nextDouble() - 0.5f;
        float randomZ = 0;
        if (enable3D) {
            randomZ = (float) randomGenerator.nextDouble() - 0.5f;
        }
        particleVelocity.x = (randomX + initialVelocity.x + dx / 10) / 120;
        particleVelocity.y = (randomY + initialVelocity.y + dy / 10) / 120;
        if (enable3D) {
            particleVelocity.z = (randomZ + initialVelocity.z) / 120;
        }
        particleVelocity.scale(velocityModifier);
        return new Particle(particleLocation, particleVelocity, particleLifeTime);
    }

    public void update() {
        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            particle.update(gravity);
            if (particle.isDestroyed()) {
                particles.remove(i);
                i--;
            }
        }
        if (enable3D) {
            if (!Mouse.isButtonDown(0)) {
                for (int i = 0; i < spawningRate; i++) {
                    particles.add(generateNewParticle(0, 0));
                }
            }
        } else {
            float mouseX = ((float) Mouse.getX() / Display.getWidth() - 0.5f) * 2;
            float mouseY = ((float) Mouse.getY() / Display.getHeight() - 0.5f) * 2;
            if (Mouse.isButtonDown(0)) {
                location.set(mouseX, mouseY);
                int dx = Mouse.getDX();
                int dy = Mouse.getDY();
                for (int i = 0; i < spawningRate; i++) {
                    particles.add(generateNewParticle(dx, dy));
                }
            }
        }
    }

    public void draw() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glBegin(GL_POINTS);
        for (Particle particle : particles) {
            float colour = (float) particle.expireTime / particleLifeTime;
            glColor4f(colorVec.x, colorVec.y * colour, colorVec.z * colour, colour + 0.6f);
            glVertex3f(particle.position.x, particle.position.y, particle.position.z);
        }
        glEnd();
        glPopAttrib();
    }

    private static class Particle {

        public Vector3f position;
        public Vector3f velocity;
        public int expireTime;

        private Particle(Vector3f position, Vector3f velocity, int expireTime) {
            this.position = position;
            this.velocity = velocity;
            this.expireTime = expireTime;
        }

        public boolean isDestroyed() {
            return expireTime <= 0;
        }

        public void update(Vector3f gravity) {
            position.translate(velocity.x, velocity.y, velocity.z);
            velocity.translate(gravity.x, gravity.y, gravity.z);
            expireTime -= 1;
        }

        @Override
        public String toString() {
            return "Particle{" +
                    "position=" + position +
                    ", velocity=" + velocity +
                    ", expireTime=" + expireTime +
                    '}';
        }
    }
}
