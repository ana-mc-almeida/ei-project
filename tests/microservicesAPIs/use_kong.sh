#!/bin/bash

TERRAFORM_DIR="../../terraform"
TEST_DIR="$(pwd)"
KONG_KEY="KongAddress"
DEFAULT_PORT=8000

scripts=(
  "crossSellingRecomendations.sh"
  "customer.sh"
  "discountCoupon.sh"
  "loyaltyCard.sh"
  "ollama.sh"
  "purchase.sh"
  "selledProductRecommendation.sh"
  "shops.sh"
)

echo "📦 Executando terraform output em: $TERRAFORM_DIR"
terraform_output=$(cd "$TERRAFORM_DIR" && terraform output)

echo "🔍 Terraform Output:"
echo "----------------------------"
echo "$terraform_output"
echo "----------------------------"

# Extrair o endereço do Kong
kong_address=$(echo "$terraform_output" | grep "^$KONG_KEY" | grep -oE "ec2-[^\"]+")

if [[ -z "$kong_address" ]]; then
  echo "❌ Não foi possível encontrar a chave '$KONG_KEY' no terraform output."
  exit 1
fi

echo "🌐 Endereço do Kong encontrado: $kong_address"

# Atualizar scripts
for script in "${scripts[@]}"; do
  script_path="$TEST_DIR/$script"

  if [[ -f "$script_path" ]]; then
    echo "🛠️  Atualizando $script com Kong DNS: $kong_address e PORT: $DEFAULT_PORT"

    # Atualizar EC2_DNS
    sed -i.bak -E "s|^EC2_DNS=\"[^\"]*\"|EC2_DNS=\"$kong_address\"|" "$script_path"

    # Atualizar PORT
    sed -i.bak -E "s|^PORT=[0-9]+|PORT=$DEFAULT_PORT|" "$script_path"

    rm -f "$script_path.bak"
  else
    echo "❌ Script não encontrado: $script_path"
  fi
done

echo "✅ Todos os scripts foram atualizados com o endereço do Kong e porta 8000!"
