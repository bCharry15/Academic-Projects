library verilog;
use verilog.vl_types.all;
entity PROYECTO_TOP is
    port(
        Cout            : out    vl_logic;
        CLOCK_50        : in     vl_logic;
        PC              : out    vl_logic_vector(2 downto 0);
        PULSO           : out    vl_logic;
        CLK             : in     vl_logic;
        Reset           : in     vl_logic;
        SIM_MODE        : in     vl_logic;
        ACC             : out    vl_logic_vector(7 downto 0);
        HEX0            : out    vl_logic_vector(6 downto 0);
        HEX1            : out    vl_logic_vector(6 downto 0);
        ROM_OUT         : out    vl_logic_vector(10 downto 0)
    );
end PROYECTO_TOP;
