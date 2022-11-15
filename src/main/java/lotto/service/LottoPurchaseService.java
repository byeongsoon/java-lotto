package lotto.service;

import camp.nextstep.edu.missionutils.Console;

import lotto.domain.Lotto;
import lotto.domain.LottoFactory;
import lotto.domain.User;
import lotto.printer.RequestInputPrinter;
import lotto.printer.SystemGuidePrinter;

import java.util.List;

import static lotto.error.ErrorMessage.PAYMENT_AMOUNT_NOT_NUMBER;

public class LottoPurchaseService {

    private User user;

    public User getUser() {
        return user;
    }

    public void startLotto() throws IllegalArgumentException {
        int paymentAmount = enterPaymentAmount();
        int lottoGameCount = calculateLottoCount(paymentAmount);

        generateUser(generateLottos(lottoGameCount),paymentAmount);
    }

    private void generateUser(List<Lotto> lottos, int amount) {
        user = new User(lottos, amount);
    }

    private Integer enterPaymentAmount() throws IllegalArgumentException {
        RequestInputPrinter.paymentAmountInputGuide();
        String input = Console.readLine();
        if (!isDigit(input)) {
            throw new IllegalArgumentException(PAYMENT_AMOUNT_NOT_NUMBER.getMessage());
        }
        return Integer.parseInt(input);
    }

    private boolean isDigit(String paymentAmount) {
        return paymentAmount.chars()
            .allMatch(Character::isDigit);
    }

    private Integer calculateLottoCount(int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException();
        }
        if (amount % 1000 != 0) {
            throw new IllegalArgumentException();
        }
        return amount / 1000;
    }

    private List<Lotto> generateLottos(int count) {
        List<Lotto> lottos = LottoFactory.generateLotto(count);
        SystemGuidePrinter.purchaseHistoryMessage(lottos);

        return lottos;
    }

}
