package com.example.rattrapagepi.fonctionnalites;

import java.time.Month;

public class MoisEtAnnee implements Comparable<MoisEtAnnee> {
    private final Month mois;
    private final int annee;

    public MoisEtAnnee(Month mois, int annee) {
        this.mois = mois;
        this.annee = annee;
    }

    @Override
    public int compareTo(MoisEtAnnee other) {
        int yearComparison = Integer.compare(this.annee, other.annee);
        if (yearComparison != 0) {
            return yearComparison;
        }
        return this.mois.compareTo(other.mois);
    }

    @Override
    public String toString() {
        return mois.toString() + " " + annee;
    }

}
