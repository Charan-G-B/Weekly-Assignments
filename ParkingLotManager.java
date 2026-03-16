
class ParkingSpot {

    String licensePlate;
    long entryTime;
    boolean occupied;

    public ParkingSpot() {
        this.occupied = false;
    }
}

public class ParkingLotManager {

    private ParkingSpot[] parkingLot;
    private int capacity;

    public ParkingLotManager(int capacity) {

        this.capacity = capacity;
        parkingLot = new ParkingSpot[capacity];

        for (int i = 0; i < capacity; i++) {
            parkingLot[i] = new ParkingSpot();
        }
    }

    // Hash function
    private int hash(String licensePlate) {
        return Math.abs(licensePlate.hashCode()) % capacity;
    }

    // Park vehicle using linear probing
    public void parkVehicle(String licensePlate) {

        int index = hash(licensePlate);
        int probes = 0;

        while (parkingLot[index].occupied) {
            index = (index + 1) % capacity;
            probes++;
        }

        parkingLot[index].licensePlate = licensePlate;
        parkingLot[index].entryTime = System.currentTimeMillis();
        parkingLot[index].occupied = true;

        System.out.println(
            "Assigned spot #" + index + " (" + probes + " probes)"
        );
    }

    // Vehicle exit
    public void exitVehicle(String licensePlate) {

        int index = hash(licensePlate);

        while (parkingLot[index].occupied) {

            if (parkingLot[index].licensePlate.equals(licensePlate)) {

                long duration =
                        (System.currentTimeMillis() - parkingLot[index].entryTime) / 1000;

                double fee = duration * 0.05; // simple billing

                parkingLot[index].occupied = false;

                System.out.println(
                    "Spot #" + index +
                    " freed. Duration: " + duration +
                    " sec Fee: $" + fee
                );

                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found");
    }

    // Statistics
    public void getStatistics() {

        int occupied = 0;

        for (ParkingSpot spot : parkingLot) {
            if (spot.occupied) occupied++;
        }

        double occupancy = (occupied * 100.0) / capacity;

        System.out.println("Occupancy: " + occupancy + "%");
    }

    public static void main(String[] args) {

        ParkingLotManager lot = new ParkingLotManager(500);

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}