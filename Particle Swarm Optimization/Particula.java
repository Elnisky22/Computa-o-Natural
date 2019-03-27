package pso;

import java.util.Random;

/**
 * Representa a part�cula de Particle Swarm Optimization.
 */
class Particula {

    private Vetor position;		// Posi��o atual.
    private Vetor velocity;		// Velocidade.
    private Vetor bestPosition;	// Melhor posi��o local.
    private double bestEval;        // Melhor valor local.

    /**
     * Gera a part�cula com uma posi��o inicial rand�mica.
     * @param beginRange    o menor XY da posi��o
     * @param endRange      o maior XY da posi��o
     */
    Particula (int beginRange, int endRange) {
        if (beginRange >= endRange) {
            throw new IllegalArgumentException("O in�cio do intervalo deve ser menor que o final.");
        }
        position = new Vetor();
        velocity = new Vetor();
        setRandomPosition(beginRange, endRange);
        bestPosition = velocity.clone();
        bestEval = eval();
    }

    /**
     * Calcula a qualidade da part�cula.
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
     * Gera valores aleat�rios dentro de um intervalo.
     * @param beginRange    valor m�nimo
     * @param endRange      valor m�ximo
     * @return              o valor aleat�rio gerado
     */
    private static int rand (int beginRange, int endRange) {
        Random r = new java.util.Random();
        return r.nextInt(endRange - beginRange) + beginRange;
    }

    /**
     * Atualiza a melhor avalia��o local, se for melhor.
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