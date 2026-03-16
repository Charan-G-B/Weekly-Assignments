import java.util.*;

class Transaction {

    int id;
    int amount;
    String merchant;
    String account;

    public Transaction(int id, int amount, String merchant, String account) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
    }
}

public class TransactionAnalyzer {

    // Classic Two Sum
    public static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                System.out.println(
                    "Two-Sum Match: Transaction " +
                    map.get(complement).id + " + " + t.id
                );

                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No pair found");
    }

    // Detect duplicate payments
    public static void detectDuplicates(List<Transaction> transactions) {

        HashMap<Integer, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            map.putIfAbsent(t.amount, new ArrayList<>());
            map.get(t.amount).add(t);
        }

        for (int amount : map.keySet()) {

            List<Transaction> list = map.get(amount);

            if (list.size() > 1) {

                System.out.println("Duplicate amount detected: " + amount);

                for (Transaction t : list) {
                    System.out.println("Transaction ID: " + t.id);
                }
            }
        }
    }

    // K-Sum (simple recursive)
    public static void kSum(
        List<Transaction> transactions,
        int start,
        int k,
        int target,
        List<Transaction> current
    ) {

        if (k == 0 && target == 0) {

            System.out.print("K-Sum Match: ");
            for (Transaction t : current) {
                System.out.print(t.id + " ");
            }
            System.out.println();

            return;
        }

        if (k == 0 || start >= transactions.size()) return;

        for (int i = start; i < transactions.size(); i++) {

            current.add(transactions.get(i));

            kSum(
                transactions,
                i + 1,
                k - 1,
                target - transactions.get(i).amount,
                current
            );

            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1"));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2"));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3"));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4"));

        findTwoSum(transactions, 500);

        detectDuplicates(transactions);

        kSum(transactions, 0, 3, 1000, new ArrayList<>());
    }
}