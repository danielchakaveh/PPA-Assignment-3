public abstract class Disease {
    // The chance of an animal dying from the disease at any step
    private double mortalityRate;
    // The chance of a disease spreading to another being at any step
    private double contagiousness;

    /**
     *
     * @param contagiousness The chance of the disease spreading to another being upon contact with an infected
     * @param mortalityRate The chance of an infected dying at any step
     */
    public Disease(double contagiousness, double mortalityRate)
    {
        this.contagiousness = contagiousness;
        this.mortalityRate = mortalityRate;
    }

    /**
     * slightly changes the infected organism
     * @param infected The organism which has the disease
     */
    public abstract void affect(Organism infected);
}
