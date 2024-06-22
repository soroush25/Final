package src.model.bl;

import lombok.Getter;
import src.controller.exceptions.NotFoundException;
import src.model.da.ReceiptDa;
import src.model.entity.Receipt;
import src.model.tools.CRUD;

import java.util.List;

public class ReceiptBl implements CRUD<Receipt> {
    @Getter
    private static ReceiptBl receiptBl = new ReceiptBl();

    private ReceiptBl() {
    }

    @Override
    public Receipt save(Receipt receipt) throws Exception {
        try (ReceiptDa receiptDa = new ReceiptDa()) {
            receiptDa.save(receipt);
            return receipt;
        }
    }

    @Override
    public Receipt edit(Receipt receipt) throws Exception {
        try (ReceiptDa receiptDa = new ReceiptDa()) {
            if (receiptDa.findById(receipt.getId()) != null) {
                receiptDa.edit(receipt);
                return receipt;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Receipt remove(int id) throws Exception {
        try (ReceiptDa receiptDa = new ReceiptDa()) {
            Receipt receipt = receiptDa.findById(id);
            if (receipt != null) {
                receiptDa.remove(id);
                return receipt;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public List<Receipt> findAll() throws Exception {
        try (ReceiptDa receiptDa = new ReceiptDa()) {
            List<Receipt> receiptList = receiptDa.findAll();
            if (!receiptList.isEmpty()) {
                for (Receipt receipt : receiptList) {
//                    receipt.setAmount(TransactionBl.getTransactionBl().findById(receipt.getAmount().getId()));
                    receipt.setTransactionDateTime(TransactionBl.getTransactionBl().findByDateTimeReport(receipt.getTransactionDateTime().getTransactionDateTime()));
                    receipt.setSourceAccount(AccountBl.getAccountBl().findByAccountNumber(receipt.getSourceAccount().getAccountNumber()));
                    receipt.setDestinationAccount(AccountBl.getAccountBl().findByAccountNumber(receipt.getDestinationAccount().getAccountNumber()));
                }
                return receiptList;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Receipt findById(int id) throws Exception {
        try (ReceiptDa receiptDa = new ReceiptDa()) {
            Receipt receipt = receiptDa.findById(id);
            if (receipt != null) {
//receipt.setAmount(TransactionBl.getTransactionBl().findById(receipt.getAmount().getId()));
                receipt.setTransactionDateTime(TransactionBl.getTransactionBl().findByDateTimeReport(receipt.getTransactionDateTime().getTransactionDateTime()));
                receipt.setSourceAccount(AccountBl.getAccountBl().findByAccountNumber(receipt.getSourceAccount().getAccountNumber()));
                receipt.setDestinationAccount(AccountBl.getAccountBl().findByAccountNumber(receipt.getDestinationAccount().getAccountNumber()));
                return receipt;
            } else {
                throw new NotFoundException();
            }
        }
    }
}
