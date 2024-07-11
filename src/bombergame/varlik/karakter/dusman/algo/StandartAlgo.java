package bombergame.varlik.karakter.dusman.algo;

public class StandartAlgo extends Algoritma {
    @Override
    public int yonBelirle() {
        return rastgele.nextInt(4);
    }
}
