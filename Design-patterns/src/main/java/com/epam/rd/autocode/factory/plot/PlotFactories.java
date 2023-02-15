package com.epam.rd.autocode.factory.plot;

class PlotFactories {

    public PlotFactory classicDisneyPlotFactory(Character hero, Character beloved, Character villain) {
        return () -> new Plot() {
            @Override
            public String toString() {
                return hero.name() +
                        " is great. " +
                        hero.name() +
                        " and " +
                        beloved.name() +
                        " love each other. " +
                        villain.name() +
                        " interferes with their happiness but loses in the end.";
            }
        };
    }

    public PlotFactory contemporaryDisneyPlotFactory(Character hero, EpicCrisis epicCrisis, Character funnyFriend) {
        return () -> new Plot() {
            @Override
            public String toString() {
                return hero.name() +
                        " feels a bit awkward and uncomfortable. But personal issues fades, when a big trouble comes - " +
                        epicCrisis.name() +
                        ". " +
                        hero.name() +
                        " stands up against it, but with no success at first." +
                        "But putting self together and help of friends, including spectacular funny " +
                        funnyFriend.name() +
                        " restore the spirit and " +
                        hero.name() +
                        " overcomes the crisis and gains gratitude and respect";
            }
        };
    }

    public PlotFactory marvelPlotFactory(Character[] heroes, EpicCrisis epicCrisis, Character villain) {
        return () -> new Plot() {
            @Override
            public String toString() {
                StringBuilder result = new StringBuilder();
                result.append(epicCrisis.name());
                result.append(" threatens the world. But");
                for (Character hero : heroes) {
                    result.append(" brave ")
                            .append(hero.name())
                            .append(",");
                }
                result.deleteCharAt(result.length()-1);
                result.append(" on guard. So, no way that intrigues of ");
                result.append(villain.name());
                result.append(" overcome the willpower of inflexible heroes");

                return result.toString();
            }
        };
    }
}
