create table marca(
    codigoMarca serial not null primary key,
    nomeMarca varchar(50) not null	
);



create table cargo(
    codigoCargo serial not null primary key,
    nomeCargo varchar(40) not null,	
    descCargo varchar(200) not null	
);





create table produto(
     codigoProduto serial not null primary key,
     nomeProduto varchar(40) not null,
     dataValidade Date not null,
     estoqueProduto int not null,
     vlrCusto decimal not null,
     vlrVenda decimal not null,
     codigoBarra varchar(40) not null,
     descricaoProduto varchar(800) not null
   
);



create table pessoa(
    codigoPessoa serial not null primary key,
    nomePessoa varchar(60) not null,
    dataNascimento date not null,
    cpfPessoa varchar(17) not null unique,
    rgPessoa varchar(14) not null unique,
    telefonePessoa varchar(17),
    celularPessoa varchar(17) not null,
    emailPessoa varchar(40) not null unique,
    enderecoPessoa varchar(100) not null,
    estadoPessoa varchar(2) not null,
    cepPessoa  varchar(20) not null,
    cidadePessoa varchar(20) not null,
    bairroPessoa varchar(30) not null
);

create table cliente(
    codigoCliente int not null primary key references pessoa(codigoPessoa),
    obsCliente varchar(500),
    loginCliente varchar(22) not null,
    senhaCliente varchar(16) not null
);

create table funcionario(
    codigoFuncionario int not null primary key references pessoa(codigoPessoa),
    obsFuncionario varchar(500),
    loginFuncionario varchar(22) not null,
    senhaFuncionario varchar(16) not null,
    codigoCargo int not null references cargo(codigoCargo)
);

create table Fornecedor(
    codigoFornecedor int not null primary key references pessoa(codigoPessoa),
    razaoSocial varchar(40),
    contatoVendedor varchar(30) not null,
    vlrPedido decimal not null, 
    obsFornecedor varchar(500)
);





 -- functions

create or replace function cadastrarPessoa(codigo_Pessoa int, nome_Pessoa varchar, data_Nascimento date, cpf_Pessoa varchar, rg_Pessoa varchar, telefone_Pessoa varchar, celular_Pessoa varchar, email_Pessoa varchar, endereco_Pessoa varchar, estado_Pessoa varchar, cep_Pessoa varchar, cidade_Pessoa varchar, bairro_Pessoa varchar) Returns int as $$
    declare
        retornoPessoa int := 0;
    begin
        if codigo_Pessoa > 0 then
            update pessoa set  codigoPessoa  = codigo_Pessoa, nomePessoa = nome_Pessoa, dataNascimento = data_Nascimento, cpfPessoa = cpf_Pessoa, rgPessoa = rg_Pessoa, telefonePessoa = telefone_Pessoa, celularPessoa = celular_Pessoa, emailPessoa = email_Pessoa, enderecoPessoa = endereco_Pessoa, estadoPessoa = estado_Pessoa, cepPessoa = cep_Pessoa, cidadePessoa = cidade_Pessoa, bairroPessoa = bairro_Pessoa where codigoPessoa = codigo_Pessoa returning codigoPessoa into retornoPessoa;
        else
            insert into pessoa values(default, nome_Pessoa, data_Nascimento,  cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa) returning codigoPessoa into retornoPessoa;
        end if;
        return retornoPessoa;
    end;	
$$ language plpgsql;

--Procedures 
create or replace procedure cadastrarMarca(codigo_Marca int, nome_Marca varchar) as $$
    begin
        if codigo_Marca > 0 then
            update marca set nomeMarca = nome_Marca where codigoMarca = codigo_Marca;
        else
            insert into marca values(default, nome_Marca);
        end if;
    end;
$$ language plpgsql;


create or replace procedure cadastrarCargo(codigo_Cargo int, nome_Cargo varchar, desc_Cargo varchar) as $$
    begin
        if codigo_Cargo > 0 then
            update cargo set nomeCargo = nome_Cargo, descCargo = desc_Cargo where codigoCargo = codigo_Cargo;
        else
            insert into cargo values(default, nome_Cargo, desc_Cargo);
        end if;
    end;
$$ language plpgsql;


create or replace procedure cadastrarProduto(codigo_Produto int, nome_Produto varchar, data_Validade Date, estoque_Produto int, vlr_Custo float, vlr_Venda float, codigo_Barra varchar, descricao_Produto varchar) as $$          
    begin
     
        if codigo_Produto > 0 then
           update produto set codigoProduto = codigo_Produto, nomeProduto = nome_Produto, dataValidade = data_Validade, estoqueProduto = estoque_Produto, vlrCusto = vlr_Custo, vlrVenda = vlr_Venda, codigoBarra = codigo_Barra, descricaoProduto = descricao_Produto where codigoProduto = codigo_Produto;
        else
            insert into produto values(default, nome_Produto, data_Validade, estoque_Produto, vlr_Custo, vlr_Venda, codigo_Barra, descricao_Produto);
        end if;
    end;
$$ language plpgsql;


create or replace procedure cadastrarCliente(codigo_Pessoa int, nome_Pessoa varchar, data_Nascimento date, cpf_Pessoa varchar, rg_Pessoa varchar, telefone_Pessoa varchar, celular_Pessoa varchar, email_Pessoa varchar, endereco_Pessoa varchar, estado_Pessoa varchar, cep_Pessoa varchar, cidade_Pessoa varchar, bairro_Pessoa varchar,  obs_Cliente varchar, login_Cliente varchar, senha_Cliente varchar) as $$
    declare
        idCliente int := 0;
        idPessoa int := 0;
    begin
       select into  idCliente codigoCliente from cliente where codigoCliente = codigo_Pessoa;
       select into idPessoa codigopessoa from pessoa where codigoPessoa = codigo_Pessoa;
       if idCliente > 0 and idPessoa > 0 then
           update cliente set codigoCliente = (select cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)),  obsCliente = obs_Cliente, loginCliente = login_Cliente, senhaCliente = senha_Cliente where codigoCliente = codigo_Pessoa;
       else
           
           insert into cliente values((SELECT cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)), obs_Cliente, login_Cliente, senha_Cliente );
         end if;
    end;
$$ language plpgsql;

create or replace procedure cadastrarFuncionario(codigo_Pessoa int, nome_Pessoa varchar, data_Nascimento date, cpf_Pessoa varchar, rg_Pessoa varchar, telefone_Pessoa varchar, celular_Pessoa varchar, email_Pessoa varchar, endereco_Pessoa varchar, estado_Pessoa varchar, cep_Pessoa varchar, cidade_Pessoa varchar, bairro_Pessoa varchar,  obs_Funcionario varchar, login_Funcionario varchar, senha_Funcionario varchar, codigo_Cargo int ) as $$
    declare
        idFunc int := 0;
        idPessoa int := 0;
    begin
       select into  idFunc codigoFuncionario from funcionario where codigoFuncionario = codigo_Pessoa;
       select into idPessoa codigopessoa from pessoa where codigoPessoa = codigo_Pessoa;
       if idFunc > 0 and idPessoa > 0 then
           update funcionario set codigoFuncionario = (select cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)),  obsFuncionario = obs_Funcionario, loginFuncionario = login_Funcionario, senhaFuncionario = senha_Funcionario, codigoCargo = codigo_Cargo where codigoFuncionario = codigo_Pessoa;
       else
           
           insert into funcionario values((SELECT cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)), obs_Funcionario, login_Funcionario, senha_Funcionario, codigo_Cargo );
         end if;
    end;
$$ language plpgsql;

create or replace procedure cadastrarFornecedor(codigo_Pessoa int, nome_Pessoa varchar, data_Nascimento date, cpf_Pessoa varchar, rg_Pessoa varchar, telefone_Pessoa varchar, celular_Pessoa varchar, email_Pessoa varchar, endereco_Pessoa varchar, estado_Pessoa varchar, cep_Pessoa varchar, cidade_Pessoa varchar, bairro_Pessoa varchar,  razao_Social varchar, contato_Vendedor varchar, vlr_Pedido decimal, obs_Fornecedor varchar) as $$
    declare
        idFornec int := 0;
        idPessoa int := 0;
    begin
       select into  idFornec codigoFornecedor from fornecedor where codigoFornecedor = codigo_Pessoa;
       select into idPessoa codigopessoa from pessoa where codigoPessoa = codigo_Pessoa;
       if idFornec > 0 and idPessoa > 0 then
           update fornecedor set codigoFornecedor = (select cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)),  razaoSocial= razao_Social, contatoVendedor = contato_Vendedor, vlrPedido = vlr_Pedido, obsFornecedor = obs_Fornecedor where codigoFornecedor = codigo_Pessoa;
       else
           
           insert into fornecedor values((SELECT cadastrarPessoa(codigo_Pessoa, nome_Pessoa, data_Nascimento, cpf_Pessoa, rg_Pessoa, telefone_Pessoa, celular_Pessoa, email_Pessoa, endereco_Pessoa, estado_Pessoa, cep_Pessoa, cidade_Pessoa, bairro_Pessoa)), razao_Social, contato_Vendedor, vlr_Pedido, obs_Fornecedor );
         end if;
    end;
$$ language plpgsql;




--> teste
select * from funcionario fu inner join pessoa pe on fu.codigoFuncionario = pe.codigoPessoa

call cadastrarFuncionario(0, 'aaaaa', '2002-10-10', '3311', '88', '999666', '9963255', 'eemail', 'rua 3', 'SP', '15710000', 'jales', 'centro', 'legal', 'login', 'senha', 1) 



SELECT cadastrarPessoa(0, 'aaaaa', '2002-10-10', '111111111', '8888888888', '999666', '9963255', 'email@email', 'rua 3', 'SP', '15710000', 'jales', 'centro') 

call cadastrarCliente(0, 'aaaaa', '2002-10-10', '111111111', '8888888888', '999666', '9963255', 'email@email', 'rua 3', 'SP', '15710000', 'jales', 'centro', 'legal', 'login', 'senha') 


 insert into cliente values(6,  'legal', 'login', '123456');
      
select * from cliente cl inner join pessoa pe on cl.codigoCliente = pe.codigoPessoa

SELECT * FROM pessoa

SELECT * FROM cliente








