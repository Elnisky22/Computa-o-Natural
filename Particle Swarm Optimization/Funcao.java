package pso;

class Funcao {

    /**
     * Calcula o resultado de Bukin N 6
     * Domínio [-15, -5], [-3, 3].
     * Mínimo é 0 em x = 10 e y = 1.
     * @param x     componente x
     * @param y     componente y
     * @return      componente z
     */
    static double bukin (double x, double y) {
		double term1, term2;
		term1 = 100 * Math.sqrt(Math.abs(y - 0.01 * x*x));
		term2 = 0.01 * Math.abs(x + 10);
		
		return term1 + term2;
    }

}