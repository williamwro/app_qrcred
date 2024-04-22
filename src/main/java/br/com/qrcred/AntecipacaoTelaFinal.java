package br.com.qrcred;

import static com.mikepenz.iconics.typeface.IconicsHolder.getApplicationContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AntecipacaoTelaFinal extends AppCompatActivity {
    public String Mes_Corrente = "";
    public String nome_associado_string;
    String email;
    String cel;
    String cpf;
    String matricula;
    String empregador;
    String cep;
    String endereco;
    String numero;
    String bairro;
    String cidade;
    String estado;
    String celzap;
    String senha;
    String cartao;
    String cod_situacao2;
    String valor_pedido;
    TextView tvValorPedido;
    String taxa;
    TextView tvTaxa;
    String valor_descontar;
    TextView tvValoDescontar;
    TextView tvMes;
    Button btnRetornar;

    public RequestQueue requestQueue;
    String nome_divisao;
    String limite;
    public String mensagem_email;
    String email_receber_pedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antecipacao_tela_final);
        Intent intent           = this.getIntent();
        matricula               = intent.getStringExtra("matricula");
        nome_associado_string   = intent.getStringExtra("nome");
        cep                     = intent.getStringExtra("cep");
        endereco                = intent.getStringExtra("endereco");
        numero                  = intent.getStringExtra("numero");
        bairro                  = intent.getStringExtra("bairro");
        cidade                  = intent.getStringExtra("cidade");
        estado                  = intent.getStringExtra("estado");
        celzap                  = intent.getStringExtra("celzap");
        email                   = intent.getStringExtra("email");
        cpf                     = intent.getStringExtra("cpf");
        cel                     = intent.getStringExtra("cel");
        empregador              = intent.getStringExtra("empregador");
        matricula               = intent.getStringExtra("matricula");
        senha                   = intent.getStringExtra("senha");
        cartao                  = intent.getStringExtra("cartao");
        cod_situacao2           = intent.getStringExtra("cod_situacao2");
        Mes_Corrente            = intent.getStringExtra("Mes_Corrente");
        valor_pedido            = intent.getStringExtra("valor_pedido");
        taxa                    = intent.getStringExtra("taxa");
        valor_descontar         = intent.getStringExtra("valor_descontar");
        senha                   = intent.getStringExtra("senha");
        cartao                  = intent.getStringExtra("cartao");
        nome_divisao            = intent.getStringExtra("nome_divisao");
        limite                  = intent.getStringExtra("limite");
        email_receber_pedidos   = intent.getStringExtra("email_receber_pedidos");

        tvValorPedido           = findViewById(R.id.tvValoPedido);
        btnRetornar             = findViewById(R.id.btnRetornar);

        tvValorPedido.setText(valor_pedido);

        SendEmail(valor_pedido,nome_associado_string,matricula,empregador,email_receber_pedidos);

        btnRetornar.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), Home2ActivityMenu.class);
            intent2.putExtra("matricula",matricula);
            intent2.putExtra("nome",nome_associado_string);
            intent2.putExtra("cep",cep);
            intent2.putExtra("endereco",endereco);
            intent2.putExtra("numero",numero);
            intent2.putExtra("bairro",bairro);
            intent2.putExtra("cidade",cidade);
            intent2.putExtra("estado",estado);
            intent2.putExtra("celzap",celzap);
            intent2.putExtra("email",email);
            intent2.putExtra("cpf",cpf);
            intent2.putExtra("cel",cel);
            intent2.putExtra("empregador",empregador);
            intent2.putExtra("matricula",matricula);
            intent2.putExtra("senha",senha);
            intent2.putExtra("cartao",cartao);
            intent2.putExtra("cod_situacao2",cod_situacao2);
            intent2.putExtra("Mes_Corrente",Mes_Corrente);
            intent2.putExtra("catao",cod_situacao2);
            intent2.putExtra("senha",Mes_Corrente);
            intent2.putExtra("nome_divisao",nome_divisao);
            intent2.putExtra("limite",limite);
            startActivity(intent2);
        });

    }
    private void email_html(String valor_solicitado, String nome, String matricula, String empregador){
        mensagem_email = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"+
                "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office'>"+
                "<head>"+
                "<meta charset='UTF-8'>"+
                "<meta content='width=device-width, initial-scale=1' name='viewport'>"+
                "<meta name='x-apple-disable-message-reformatting'>"+
                "<meta http-equiv='X-UA-Compatible' content='IE=edge'>"+
                "<meta content='telephone=no' name='format-detection'>"+
                "<title></title>"+
                "<!--[if (mso 16)]>"+
                "<style type='text/css'>"+
                "a {text-decoration: none;}"+
                "</style>"+
                "<![endif]-->"+
                "<!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->"+
                "<!--[if gte mso 9]>"+
                "<xml>"+
                "<o:OfficeDocumentSettings>"+
                "<o:AllowPNG></o:AllowPNG>"+
                "<o:PixelsPerInch>96</o:PixelsPerInch>"+
                "</o:OfficeDocumentSettings>"+
                "</xml>"+
                "<![endif]-->"+
                "<!--[if !mso]><!-- -->"+
                "<link href='https://fonts.googleapis.com/css?family=Lato:400,400i,700,700i' rel='stylesheet'>"+
                "<!--<![endif]-->"+
                "</head>"+

                "<body>"+
                "<div class='es-wrapper-color'>"+
                "<!--[if gte mso 9]>"+
                "<v:background xmlns:v='urn:schemas-microsoft-com:vml' fill='t'>"+
                "<v:fill type='tile' color='#f4f4f4'></v:fill>"+
                "</v:background>"+
                "<![endif]-->"+
                "<table class='es-wrapper' width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr class='gmail-fix' height='0'>"+
                "<td>"+
                "<table width='600' cellspacing='0' cellpadding='0' border='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td cellpadding='0' cellspacing='0' border='0' style='line-height: 1px; min-width: 600px;' height='0'><img src='' style='display: block; max-height: 0px; min-height: 0px; min-width: 600px; width: 600px;' alt width='600' height='1'></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-email-paddings' valign='top'>"+
                "<table class='es-header esd-header-popover' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td esd-custom-block-id='6339' align='center' bgcolor='#999999' style='background-color: #999999;'>"+
                "<table class='es-header-body' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure es-p20t es-p10b es-p10r es-p10l' align='left' bgcolor='#999999' style='background-color: #999999;'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='580' valign='top' align='center'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                " <tbody>"+
                "<tr>"+
                "<td align='center' class='esd-block-image' style='font-size: 0px;'><a target='_blank'><img class='adapt-img' src='' alt style='display: block;' width='200'></a></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "<table class='es-content' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td style='background-color: #999999;' esd-custom-block-id='6340' bgcolor='#999999' align='center'>"+
                "<table class='es-content-body' style='background-color: transparent;' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure' align='left'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='600' valign='top' align='center'>"+
                "<table style='background-color: #ffffff; border-radius: 4px; border-collapse: separate;' width='100%' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-block-text es-p35t es-p5b es-p30r es-p30l' align='center'>"+
                "<h1>Solicitação de credito via App.</h1>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-spacer es-p5t es-p5b es-p20r es-p20l' bgcolor='#ffffff' align='center' style='font-size:0'>"+
                "<table width='100%' height='100%' cellspacing='0' cellpadding='0' border='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td style='border-bottom: 1px solid #ffffff; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;'></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "<table class='es-content esd-footer-popover' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td align='center'>"+
                "<table class='es-content-body' style='background-color: transparent;' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure' align='left'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='600' valign='top' align='center'>"+
                "<table style='border-radius: 4px; border-collapse: separate; background-color: #ffffff;' width='100%' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p20b es-p30r es-p30l es-m-txt-l' bgcolor='#ffffff' align='left'>"+
                "<p>Segue os dados solicitados.</p>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p30r es-p30l es-m-txt-l' align='left'>"+
                "<p>Matricula :&nbsp;<h2>" + matricula + "</h2><br>"+
                "Nome :&nbsp;<h2>"+ nome + "</h2><br>"+
                "Empregador :&nbsp;<h2>"+ empregador + "</h2><br>"+
                "Valor :&nbsp;<h2>"+ valor_solicitado + "</h2></p>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p40b es-p30r es-p30l es-m-txt-l' align='left'>"+
                "<p>Atenciosamente,</p>"+
                "<p>QrCred Tecnologia</p>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</div>"+
                "</body>"+

                "</html>";

    }
    public void SendEmail(String valor_solicitado, String nome, String matricula, String empregador, String Email_destino){

        try {
            email_html(valor_solicitado,nome,matricula,empregador);
            String stringSenderEmail = "qrcredq@gmail.com";
            String stringPasswordSenderEmail = "vsmn dlbl acsz zukc";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Email_destino));

            mimeMessage.setSubject("Dados login App QrCred");


            mimeMessage.setContent(mensagem_email,"text/html");

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
