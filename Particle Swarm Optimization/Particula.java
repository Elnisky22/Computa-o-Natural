package pso;

import java.util.Random;

/**
 * Representa a partícula de Particle Swarm Optimization.
 */
class Particula {

    private Vetor position;		// Posição atual.
    private Vetor velocity;		// Velocidade.
    private Vetor bestPosition;	// Melhor posição local.
    private double bestEval;        // Melhor valor local.

    /**
     * Gera a partícula com uma posição inicial randômica.
     * @param beginRange    o menor XY da posição
     * @param endRange      o maior XY da posição
     */
    Particula (int beginRange, int endRange) {
        if (beginRange >= endRange) {
            throw new IllegalArgumentException("O início do intervalo deve ser menor que o final.");
        }
        position = new Vetor();
        velocity = new Vetor();
        setRandomPosition(beginRange, endRange);
        bestPosition = velocity.clone();
        bestEval = eval();
    }

    /**
     * Calcula a qualidade da partícula.
     * @return      a qualidade
     */
    private double eval () {
        return Funcao.bukin(position.getX(), position.getY());
    }

    private void setRandomPosition (int beginRange, int endRange) {
        int x = rand(beginRange, endRange);
        int y = rand(beginRange, endRange);
        int z = rand(beginRange, endRange);
        position.set(x, y, z);
    }

    /**
     * Gera valores aleatórios dentro de um intervalo.
     * @param beginRange    valor mínimo
     * @param endRange      valor máximo
     * @return              o valor aleatório gerado
     */
    private static int rand (int beginRange, int endRange) {
        Random r = new java.util.Random();
        return r.nextInt(endRange - beginRange) + beginRange;
    }

    /**
     * Atualiza a melhor avaliação local, se for melhor.
     */
    void updatePersonalBest () {
        double eval = eval();
        if (eval < bestEval) {
            bestPosition = position.clone();
            bestEval = eval;
        }
    }

    /**
     * Get a copy of the position of the particle.
     * @return  the x position
     */
    Vetor getPosition () {
        return position.clone();
    }

    /**
     * Get a copy of the velocity of the particle.
     * @return  the velocity
     */
    Vetor getVelocity () {
        return velocity.clone();
    }

    /**
     * Get a copy of the personal best solution.
     * @return  the best position
     */
    Vetor getBestPosition() {
        return bestPosition.clone();
    }

    /**
     * Get the value of the personal best solution.
     * @return  the evaluation
     */
    double getBestEval () {
        return bestEval;
    }

    /**
     * Update the position of a particle by adding its velocity to its position.
     */
    void updatePosition () {
        this.position.add(velocity);
    }

    /**
     * Set the velocity of the particle.
     * @param velocity  the new velocity
     */
    void setVelocity (Vetor velocity) {
        this.velocity = velocity.clone();
    }

}