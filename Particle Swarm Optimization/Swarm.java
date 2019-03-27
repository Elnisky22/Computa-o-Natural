package pso;

import java.util.Random;

/**
 * Representa um enxame de particulas de PSO.
 */
public class Swarm {

    private int qtdParticulas, geracoes;
    private double inertia, cognitiveComponent, socialComponent;
    private Vetor bestPosition;
    private double bestEval;
    public static final double DEFAULT_INERTIA = 0.729844;
    public static final double DEFAULT_COGNITIVE = 1.496180; // Cognitive component.
    public static final double DEFAULT_SOCIAL = 1.496180; // Social component.

    /**
     * Quando as partículas são criadas, recebem posições aleatórias dentro de um intervalo.
     * Os valores do intervalo são definidos abaixo, em beginRange e endRange.
     */
    private int beginRange, endRange;
    private static final int DEFAULT_BEGIN_RANGE = -15;
    private static final int DEFAULT_END_RANGE = -5;

    /**
     * Constrói o Swarm com valores padrões.
     * @param particulas     número de partículas
     * @param geracoes        número de gerações
     */
    public Swarm (int particulas, int geracoes) {
        this(particulas, geracoes, DEFAULT_INERTIA, DEFAULT_COGNITIVE, DEFAULT_SOCIAL);
    }

    /**
     * Construct the Swarm with custom values.
     * @param particulas     the number of particles to create
     * @param geracoes        the number of generations
     * @param inertia       the particles resistance to change
     * @param cognitive     the cognitive component or introversion of the particle
     * @param social        the social component or extroversion of the particle
     */
    public Swarm (int particulas, int geracoes, double inertia, double cognitive, double social) {
        this.qtdParticulas = particulas;
        this.geracoes = geracoes;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        double infinity = Double.POSITIVE_INFINITY;
        bestPosition = new Vetor(infinity, infinity, infinity);
        bestEval = Double.POSITIVE_INFINITY;
        beginRange = DEFAULT_BEGIN_RANGE;
        endRange = DEFAULT_END_RANGE;
    }
    
    /**
     * Gerar partículas com posições inicias randômicas.
     * @return  vetor de partículas
     */
    private Particula[] initialize () {
        Particula[] particles = new Particula[qtdParticulas];
        for (int i = 0; i < qtdParticulas; i++) {
            Particula particle = new Particula(beginRange, endRange);
            particles[i] = particle;
            updateGlobalBest(particle);
        }
        return particles;
    }

    /**
     * Executa o algoritmo.
     */
    public void run () {
        Particula[] particles = initialize();

        double oldEval = bestEval;
        System.out.println("--------------------------EXECUTANDO-------------------------");
        System.out.println("Melhor posição global (Rodada " + 0 + "):\t"  + bestEval);

        for (int i = 0; i < geracoes; i++) {

            if (bestEval < oldEval) {
                System.out.println("Melhor posição global (Rodada " + (i + 1) + "):\t" + bestEval);
                oldEval = bestEval;
            }

            for (Particula p : particles) {
                p.updatePersonalBest();
                updateGlobalBest(p);
            }

            for (Particula p : particles) {
                updateVelocity(p);
                p.updatePosition();
            }
        }

        System.out.println("---------------------------RESULTADO---------------------------");
        System.out.println("x = " + bestPosition.getX());
        System.out.println("y = " + bestPosition.getY());
        System.out.println("Melhor posição final: " + bestEval);
    }

    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * @param particle  the particle to analyze
     */
    private void updateGlobalBest (Particula particle) {
        if (particle.getBestEval() < bestEval) {
            bestPosition = particle.getBestPosition();
            bestEval = particle.getBestEval();
        }
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * @param particle  the particle to update
     */
    private void updateVelocity (Particula particle) {
        Vetor oldVelocity = particle.getVelocity();
        Vetor pBest = particle.getBestPosition();
        Vetor gBest = bestPosition.clone();
        Vetor pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // The first product of the formula.
        Vetor newVelocity = oldVelocity.clone();
        newVelocity.mul(inertia);

        // The second product of the formula.
        pBest.sub(pos);
        pBest.mul(cognitiveComponent);
        pBest.mul(r1);
        newVelocity.add(pBest);

        // The third product of the formula.
        gBest.sub(pos);
        gBest.mul(socialComponent);
        gBest.mul(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }

}