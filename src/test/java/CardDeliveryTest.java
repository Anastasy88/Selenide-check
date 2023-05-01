import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void fillingOutTheForm() {
        Configuration.holdBrowserOpen = true;
        String planningDate = generateDate(4);

        open("http://localhost:9999/");
        $("span[data-test-id=city] input").setValue("Владимир");
        $("span[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("span[data-test-id=date] input").sendKeys(planningDate);
        $("span[data-test-id=name] input").setValue("Харитонова Анастасия");
        $("span[data-test-id=phone] input").setValue("+79209277455");
        $("label[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("div[data-test-id=notification]")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
