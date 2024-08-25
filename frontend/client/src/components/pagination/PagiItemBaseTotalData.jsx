const PagiItemBaseTotalData = ({ children, onClick, className }) => {
    return (
        <button
            className={className}
            onClick={onClick}
            type="button"
        >
            {children}
        </button>
    );
};

export default PagiItemBaseTotalData;
