package klerer.citibike.json;

public class Stations {
    public Data data;

    public Station[] concatenate(Station[] first, Station[] second) {
        Station[] result = new Station[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
