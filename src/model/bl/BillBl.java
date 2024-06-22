package src.model.bl;

import lombok.Getter;
import src.controller.exceptions.NotFoundException;
import src.model.da.BillDa;
import src.model.entity.Bill;
import src.model.tools.CRUD;

import java.util.List;

public class BillBl implements CRUD<Bill> {
    @Getter
    private static BillBl billBl = new BillBl();

    private BillBl() {
    }

    @Override
    public Bill save(Bill bill) throws Exception {
        try (BillDa billDa = new BillDa()) {
            billDa.save(bill);
            return bill;
        }
    }

    @Override
    public Bill edit(Bill bill) throws Exception {
        try (BillDa billDa = new BillDa()) {
            if (billDa.findById(bill.getId()) != null) {
                billDa.edit(bill);
                return bill;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Bill remove(int id) throws Exception {
        try (BillDa billDa = new BillDa()) {
            Bill bill = billDa.findById(id);
            if (bill != null) {
                billDa.remove(id);
                return bill;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public List<Bill> findAll() throws Exception {
        try (BillDa billDa = new BillDa()) {
            List<Bill> billList = billDa.findAll();
            if (!billList.isEmpty()) {
                for (Bill bill : billList) {
                    bill.setCustomerId(CustomerBl.getCustomerBl().findById(bill.getCustomerId().getId()));
                }
                return billList;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Bill findById(int id) throws Exception {
        try (BillDa billDa = new BillDa()) {
            Bill bill = billDa.findById(id);
            if (bill != null) {
                bill.setCustomerId(CustomerBl.getCustomerBl().findById(bill.getCustomerId().getId()));
                return bill;
            } else {
                throw new NotFoundException();
            }
        }
    }
}
