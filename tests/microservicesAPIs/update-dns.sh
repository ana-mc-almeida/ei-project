#!/bin/bash

set -e

TERRAFORM_DIR="../../terraform"
TEST_DIR="$(pwd)"

echo "📦 Executando terraform output em: $TERRAFORM_DIR"
terraform_output=$(cd "$TERRAFORM_DIR" && terraform output)

echo "🔍 Terraform Output:"
echo "----------------------------"
echo "$terraform_output"
echo "----------------------------"

declare -A dns_map

current_key=""
list_mode=0

while IFS= read -r line; do
  if [[ $line =~ ^([a-zA-Z0-9_-]+)[[:space:]]*=[[:space:]]*\"(ec2-[^\"]+)\" ]]; then
    key="${BASH_REMATCH[1]}"
    value="${BASH_REMATCH[2]}"
    dns_map["$key"]="$value"

  elif [[ $line =~ ^([a-zA-Z0-9_-]+)[[:space:]]*=[[:space:]]*(\[|tolist\(\[)$ ]]; then
    current_key="${BASH_REMATCH[1]}"
    list_mode=1
    continue

  elif [[ $list_mode -eq 1 && $line =~ \"(ec2-[^\"]+)\" ]]; then
    dns_map["$current_key"]="${BASH_REMATCH[1]}"
    list_mode=0
    current_key=""
  fi
done <<< "$terraform_output"

declare -A test_dns_keys=(
  ["crossSellingRecomendations.sh"]="discountCoupon-crossSellingRecommendationAddress"
  ["customer.sh"]="purchases-customerAddress"
  ["discountCoupon.sh"]="discountCoupon-crossSellingRecommendationAddress"
  ["loyaltyCard.sh"]="shop-loyaltycard-selledProductAddress"
  ["ollama.sh"]="ollama_address"
  ["purchase.sh"]="purchases-customerAddress"
  ["selledProductRecommendation.sh"]="shop-loyaltycard-selledProductAddress"
  ["shops.sh"]="shop-loyaltycard-selledProductAddress"
)

for script in "${!test_dns_keys[@]}"; do
  dns_key="${test_dns_keys[$script]}"
  dns="${dns_map[$dns_key]}"

  if [[ -z "$dns" ]]; then
    echo "⚠️  DNS não encontrado para a chave: $dns_key. Pulando $script."
    continue
  fi

  script_path="$TEST_DIR/$script"

  if [[ -f "$script_path" ]]; then
    echo "🛠️  Atualizando $script com DNS: $dns"
    sed -i.bak -E "s|^EC2_DNS=\"[^\"]*\"|EC2_DNS=\"$dns\"|" "$script_path"
    rm -f "$script_path.bak"
  else
    echo "❌ Script não encontrado: $script_path"
  fi
done

echo "✅ Todos os scripts foram atualizados com sucesso!"
