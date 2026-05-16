@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {

        return "auth/login";

    }

}