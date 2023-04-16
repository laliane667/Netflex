package dependencies.Model;

import java.sql.Date;

public class UserContract {
    private int contractsId;
    private int usersId;
    private Date contractStart;

    public UserContract(int contractsId, int usersId, Date contractStart) {
        this.contractsId = contractsId;
        this.usersId = usersId;
        this.contractStart = contractStart;
    }
    public UserContract(int usersId, Date contractStart) {
        this.usersId = usersId;
        this.contractStart = contractStart;
    }
    public int getContractId() {
        return contractsId;
    }

    public void setContractId(int contractsId) {
        this.contractsId = contractsId;
    }

    public int getUserId() {
        return usersId;
    }

    public void setUserId(int usersId) {
        this.usersId = usersId;
    }

    public Date getContractStart() {
        return contractStart;
    }

    public void setContractStart(Date contractStart) {
        this.contractStart = contractStart;
    }
}
