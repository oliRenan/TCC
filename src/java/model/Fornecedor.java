
package model;


public class Fornecedor extends Pessoa {
    
    private String razaoSocial;
    private String contatoVendedor;
    private Double vlrPedido;
    private String obsFornecedor;

    public Fornecedor(String razaoSocial, String contatoVendedor, Double vlrPedido, String obsFornecedor, int codigoPessoa, String nomePessoa, String dataNascimento, String cpfPessoa, String rgPessoa, String telefonePessoa, String celularPessoa, String emailPessoa, String enderecoPessoa, String estadoPessoa, String cepPessoa, String cidadePessoa, String bairroPessoa) {
        super(codigoPessoa, nomePessoa, dataNascimento, cpfPessoa, rgPessoa, telefonePessoa, celularPessoa, emailPessoa, enderecoPessoa, estadoPessoa, cepPessoa, cidadePessoa, bairroPessoa);
        this.razaoSocial = razaoSocial;
        this.contatoVendedor = contatoVendedor;
        this.vlrPedido = vlrPedido;
        this.obsFornecedor = obsFornecedor;
    }

    public Fornecedor() {
        super(0, "", "", "", "", "", "", "", "", "", "", "", "");
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getContatoVendedor() {
        return contatoVendedor;
    }

    public void setContatoVendedor(String contatoVendedor) {
        this.contatoVendedor = contatoVendedor;
    }

    public Double getVlrPedido() {
        return vlrPedido;
    }

    public void setVlrPedido(Double vlrPedido) {
        this.vlrPedido = vlrPedido;
    }

    public String getObsFornecedor() {
        return obsFornecedor;
    }

    public void setObsFornecedor(String obsFornecedor) {
        this.obsFornecedor = obsFornecedor;
    }

  
    
    
}
