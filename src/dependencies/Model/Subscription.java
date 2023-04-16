package dependencies.Model;

public class Subscription {
    private int id;
    private int userId;
    private int subscriberId;

    public Subscription(int userId, int subscriberId) {
        this.userId = userId;
        this.subscriberId = subscriberId;
    }

    public Subscription(int id, int userId, int subscriberId) {
        this.id = id;
        this.userId = userId;
        this.subscriberId = subscriberId;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }
}
